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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private PromoCodeClient promoCodeClient;

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
        EventDto eventDto = eventClient.getEventForBooking(eventId);

        logger.info("Event : {} ", eventDto);

        PromoCodeDto promoCodeDto = promoCodeClient.getPromoCodeForBooking(promoCodeId);
        booking.setPromoCode(promoCodeDto.getPromoCode());

        logger.info("PromoCode: {}", promoCodeDto);

        if (Objects.equals(booking.getEventId(), eventDto.getEventId())) {
            if (booking.getNumberOfTickets() > eventDto.getSeats()) {
                throw new InsufficientSeatsException("Seat Number Exceeded");
            } else {
                eventDto.setSeats(eventDto.getSeats() - booking.getNumberOfTickets());
                eventClient.updateEventForBooking(eventDto, eventDto.getEventId());
            }
            if (booking.getPrice() > eventDto.getEventPrice() || booking.getPrice() < eventDto.getEventPrice()) {
                throw new IncorrectAmountException("Please Enter the Correct Amount.");
            }
        }

        if (Objects.equals(promoCodeDto.getPromoCode(), booking.getPromoCode())) {
            int price = eventDto.getEventPrice() * bookingDto.getNumberOfTickets();
            int updatedPrice = price - promoCodeDto.getDiscount();
            booking.setPrice(updatedPrice);
        }

        //call to payment gateway

        booking.setStatus(bookingDto.getStatus());
        booking.setVenue(eventDto.getVenue());
        Booking savedBooking = bookingRepository.save(booking);

        UserDto userDto = userClient.getSingleUserForBooking(userId);

        logger.info("USER:{}", userDto);

        sendBookingNotification(savedBooking, eventDto, userDto);
        return modelMapper.map(savedBooking, BookingDto.class);
    }


    private void sendBookingNotification(Booking booking, EventDto eventDto, UserDto userDto) {
        BookingNotificationDto notificationDto = new BookingNotificationDto();
        logger.info("Sending booking notification in thread: {}", Thread.currentThread().getName());

        notificationDto.setEventName(eventDto.getEventName());
        notificationDto.setEmail(userDto.getEmailId());
        notificationDto.setNumberOfTickets(booking.getNumberOfTickets());
        notificationDto.setPrice(booking.getPrice());
        notificationDto.setUserName(userDto.getUserName());
        notificationDto.setStatus(booking.getStatus());
        notificationDto.setVenue(booking.getVenue());

        notificationClient.sendBookingNotification(notificationDto);
    }


    @Override
    public BookingDto createBookingWithoutPromoCode(BookingDto bookingDto, String eventId, String userId) {

        Booking booking = modelMapper.map(bookingDto, Booking.class);

        booking.setEventId(eventId);

        booking.setUserId(userId);

        booking.setBookingId(UUID.randomUUID().toString());

        EventDto eventDto = eventClient.getEventForBooking(eventId);
        logger.info("Event : {} ", eventDto);

        if (Objects.equals(booking.getEventId(), eventDto.getEventId())) {
            if (booking.getNumberOfTickets() > eventDto.getSeats()) {
                throw new InsufficientSeatsException("Seat Number Exceeded");
            } else {
                eventDto.setSeats(eventDto.getSeats() - booking.getNumberOfTickets());
                eventClient.updateEventForBooking(eventDto, eventDto.getEventId());
            }

            if (booking.getPrice() > eventDto.getEventPrice() || booking.getPrice() < eventDto.getEventPrice()) {
                throw new IncorrectAmountException("Please enter the correct amount.");
            }

        }
        booking.setStatus(bookingDto.getStatus());
        booking.setVenue(eventDto.getVenue());
        booking.setPromoCode("");
        Booking savedBooking = bookingRepository.save(booking);

        UserDto userDto = userClient.getSingleUserForBooking(userId);

        sendBookingNotification(savedBooking, eventDto, userDto);

        return modelMapper.map(savedBooking, BookingDto.class);
    }


    @Override
    public void deleteBooking(String bookingId) {
        Booking deletedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking Not Found With Given Id" + bookingId));
        bookingRepository.delete(deletedBooking);
    }

    @Override
    public BookingDto getSingleBooking(String bookingId) {
        Booking singleBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        return modelMapper.map(singleBooking, BookingDto.class);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> allBookings = bookingRepository.findAll();
        return allBookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
    }
}
