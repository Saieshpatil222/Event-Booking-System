package com.booking.service.impl;

import com.booking.dto.*;
import com.booking.entity.Booking;
import com.booking.exception.BookingNotFoundException;
import com.booking.exception.IncorrectAmountException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.repository.BookingRepository;
import com.booking.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.shared.dto.BookingEvent;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingNotificationService bookingNotificationService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private PromoCodeClient promoCodeClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private NotificationClient notificationClient;


    @Override
    public BookingDto createBooking(BookingDto bookingDto, String eventId, String userId, String promoCodeId) {
        Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        booking.setEventId(eventId);
        booking.setUserId(userId);
        booking.setBookingId(UUID.randomUUID().toString());
        EventDto eventDto = getEventForBooking(eventId);

        logger.info("Event : {} ", eventDto);

        UserDto userDto = userClient.getSingleUserForBooking(userId);

        PromoCodeDto promoCodeDto = getPromoCodeForBooking(promoCodeId);
        booking.setPromoCode(promoCodeDto.getPromoCode());

        logger.info("PromoCode: {}", promoCodeDto);

        if (booking.getEventId().equals(eventDto.getEventId())) {
            if (booking.getNumberOfTickets() > eventDto.getSeats()) {
                throw new InsufficientSeatsException("Seat Number Exceeded");
            }
            if (booking.getPrice() > eventDto.getEventPrice() || booking.getPrice() < eventDto.getEventPrice()) {
                throw new IncorrectAmountException("Please enter the correct amount.");
            }
        }

        int finalPrice = 0;
        int basePrice = eventDto.getEventPrice() * bookingDto.getNumberOfTickets();

        if (promoCodeDto.getPromoCode().equals(booking.getPromoCode())) {
            finalPrice = basePrice - promoCodeDto.getDiscount();
            booking.setPrice(finalPrice);
            logger.info("Applied promo code. Base price: {}, Discount: {}, Final price: {}", basePrice, promoCodeDto.getDiscount(), finalPrice);
        }

        PaymentDto payment = new PaymentDto();
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(finalPrice);
        payment.setUserId(booking.getUserId());

        PaymentDto paymentResponse = processPayment(payment, booking, finalPrice);

        if (paymentResponse == null) {
            logger.error("Payment failed for booking: {}", booking.getBookingId());
            throw new RuntimeException("Payment processing failed");
        }

//        eventDto.setSeats(eventDto.getSeats() - booking.getNumberOfTickets());
//        eventClient.updateEventForBooking(eventDto, eventDto.getEventId());

        booking.setVenue(eventDto.getVenue());
        booking.setStatus("Booking is Successful");
        booking.setUserName(userDto.getUserName());
        Booking savedBooking = bookingRepository.save(booking);


        //BookingConfirmedEvents event = new BookingConfirmedEvents(savedBooking.getBookingId(), savedBooking.getEventId(), savedBooking.getUserId(), savedBooking.getNumberOfTickets(), savedBooking.getPrice());

        BookingEvent bookingEvent = new BookingEvent(savedBooking.getBookingId(), savedBooking.getUserId(), savedBooking.getEventId(), savedBooking.getNumberOfTickets(), savedBooking.getPrice());

        kafkaTemplate.send("booking-confirmed-topic", bookingEvent);

        logger.info("Published Event {}", bookingEvent);
        bookingNotificationService.sendBookingNotification(savedBooking, eventDto, userDto);
        return modelMapper.map(savedBooking, BookingDto.class);
    }

    private PaymentDto processPayment(PaymentDto paymentDto, Booking booking, int finalPrice) {
        PaymentDto payment = new PaymentDto();
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(finalPrice);
        payment.setUserId(booking.getUserId());

        return paymentClient.createPayment(payment, payment.getBookingId(), paymentDto.getUserId());
    }

    private EventDto getEventForBooking(String eventId) {
        return eventClient.getEventForBooking(eventId);
    }

    private PromoCodeDto getPromoCodeForBooking(String promoCodeId) {
        return promoCodeClient.getPromoCodeForBooking(promoCodeId);
    }

    @Override
    public BookingDto createBookingWithoutPromoCode(BookingDto bookingDto, String eventId, String userId) {

        Booking booking = modelMapper.map(bookingDto, Booking.class);
        booking.setEventId(eventId);
        booking.setUserId(userId);
        booking.setBookingId(UUID.randomUUID().toString());

        EventDto eventDto = getEventForBooking(eventId);
        logger.info("Fetched event for booking: {}", eventDto);

        if (booking.getNumberOfTickets() > eventDto.getSeats()) {
            throw new InsufficientSeatsException("Seat Number Exceeded");
        }

        int expectedPrice = booking.getNumberOfTickets() * eventDto.getEventPrice();
        if (booking.getPrice() != eventDto.getEventPrice()) {
            throw new IncorrectAmountException("Please enter the correct amount.");
        }

        PaymentDto payment = new PaymentDto();
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(expectedPrice);
        payment.setUserId(userId);

        PaymentDto paymentResponse = processPayment(payment, booking, expectedPrice);
        if (paymentResponse == null) {
            logger.error("Payment failed for booking: {}", booking.getBookingId());
            throw new RuntimeException("Payment processing failed");
        }

        booking.setPrice(expectedPrice);
        booking.setStatus("Booking is Successful");
        booking.setVenue(eventDto.getVenue());
        booking.setPromoCode("");

        Booking savedBooking = bookingRepository.save(booking);

        UserDto userDto = userClient.getSingleUserForBooking(userId);
        bookingNotificationService.sendBookingNotification(savedBooking, eventDto, userDto);

        return modelMapper.map(savedBooking, BookingDto.class);
    }

    @Override
    public void deleteBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        paymentClient.deletePayment(bookingId);
    }

    @Override
    public BookingDto getSingleBooking(String bookingId) {
        Booking singleBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        return modelMapper.map(singleBooking, BookingDto.class);
    }

    @Override
    public Page<BookingDto> getAllBookings(int offSet, int pageSize, String field) {
        Page<Booking> allBookings = bookingRepository.findAll(PageRequest.of(offSet, pageSize).withSort(Sort.by(field)));
        return allBookings.map(booking -> modelMapper.map(booking, BookingDto.class));
    }
}
