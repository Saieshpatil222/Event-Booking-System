package com.booking.service.impl;

import com.booking.dto.BookingDto;
import com.booking.dto.EventDto;
import com.booking.dto.PromoCodeDto;
import com.booking.entity.Booking;
import com.booking.exception.BookingNotFoundException;
import com.booking.exception.IncorrectAmountException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.repository.BookingRepository;
import com.booking.service.ApiClient;
import com.booking.service.BookingService;
import com.booking.service.PromoCodeClient;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private PromoCodeClient promoCodeClient;

    @Autowired
    private WebClient webClient;

    @Override
    public BookingDto createBooking(BookingDto bookingDto, String eventId, String userId, String promoCodeId) {

        Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

        Booking booking = modelMapper.map(bookingDto, Booking.class);

        booking.setEventId(eventId);

        booking.setUserId(userId);

        booking.setBookingId(UUID.randomUUID().toString());

        EventDto eventDto = apiClient.getEventById(eventId);

        logger.info("Event : {} ", eventDto);

        PromoCodeDto promoCodeDto = promoCodeClient.getPromoCodeById(promoCodeId);

        booking.setPromoCode(promoCodeDto.getPromoCode());

        logger.info("PromoCode: {}", promoCodeDto);

        if (Objects.equals(booking.getEventId(), eventDto.getEventId())) {
            if (booking.getNumberOfTickets() > eventDto.getSeats()) {
                throw new InsufficientSeatsException("Seat Number Exceeded");
            } else {
                eventDto.setSeats(eventDto.getSeats() - booking.getNumberOfTickets());
                apiClient.updateEvent(eventDto, eventDto.getEventId());
            }

            if (booking.getPrice() > eventDto.getEventPrice() || booking.getPrice() < eventDto.getEventPrice()) {
                throw new IncorrectAmountException("Please enter the correct amount.");
            }

        }

        if (Objects.equals(promoCodeDto.getPromoCode(), booking.getPromoCode())) {
            int price = eventDto.getEventPrice() * bookingDto.getNumberOfTickets();
            int updatedPrice = price - promoCodeDto.getDiscount();
            booking.setPrice(updatedPrice);
        }
        booking.setStatus(bookingDto.getStatus());
        booking.setVenue(eventDto.getVenue());
        Booking booking1 = bookingRepository.save(booking);
        return modelMapper.map(booking1, BookingDto.class);
    }

    @Override
    public BookingDto createBookingWithoutPromoCode(BookingDto bookingDto, String eventId, String userId) {

        Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

        Booking booking = modelMapper.map(bookingDto, Booking.class);

        booking.setEventId(eventId);

        booking.setUserId(userId);

        booking.setBookingId(UUID.randomUUID().toString());

        EventDto eventDto = apiClient.getEventById(eventId);
        logger.info("Event : {} ", eventDto);

        if (Objects.equals(booking.getEventId(), eventDto.getEventId())) {
            if (booking.getNumberOfTickets() > eventDto.getSeats()) {
                throw new InsufficientSeatsException("Seat Number Exceeded");
            } else {
                eventDto.setSeats(eventDto.getSeats() - booking.getNumberOfTickets());
                apiClient.updateEvent(eventDto, eventDto.getEventId());
            }

            if (booking.getPrice() > eventDto.getEventPrice() || booking.getPrice() < eventDto.getEventPrice()) {
                throw new IncorrectAmountException("Please enter the correct amount.");
            }

        }
        booking.setStatus(bookingDto.getStatus());
        booking.setVenue(eventDto.getVenue());
        booking.setPromoCode("");
        Booking booking1 = bookingRepository.save(booking);
        return modelMapper.map(booking1, BookingDto.class);
    }


    @Override
    public void deleteBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking Not Found With Given Id" + bookingId));
        bookingRepository.delete(booking);
    }

    @Override
    public BookingDto getSingleBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
    }
}
