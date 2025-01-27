package com.booking.service;

import com.booking.dto.BookingDto;
import com.booking.dto.EventDto;
import com.booking.dto.PromoCodeDto;
import com.booking.entity.Booking;
import com.booking.exception.IncorrectAmountException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.repository.BookingRepository;
import com.booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ApiClient apiClient;

    @Mock
    private PromoCodeClient promoCodeClient;

    private BookingDto bookingDto;
    private Booking booking;
    private EventDto eventDto;
    private PromoCodeDto promoCodeDto;


    @BeforeEach
    void setUp() {

        // Initialize BookingDto
        bookingDto = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(200);
        bookingDto.setStatus("CONFIRMED");

        // Initialize Booking
        booking = new Booking();
        booking.setNumberOfTickets(2);
        booking.setPrice(200);
        booking.setStatus("CONFIRMED");

        // Initialize EventDto
        eventDto = new EventDto();
        eventDto.setEventId("event123");
        eventDto.setSeats(10);
        eventDto.setEventPrice(200); // Price per ticket
        eventDto.setAddress("123 Event Street");

        // Initialize PromoCodeDto
        promoCodeDto = new PromoCodeDto();
        promoCodeDto.setPromoCode("DISCOUNT10");
        promoCodeDto.setDiscount(10);
    }

    @Test
    public void createBookingTest() {

        BookingDto savedBookingDto = new BookingDto();
        savedBookingDto.setNumberOfTickets(2);
        savedBookingDto.setPrice(200);
        savedBookingDto.setStatus("CONFIRMED");
        savedBookingDto.setEventId("event123");
        savedBookingDto.setPromoCode("DISCOUNT10");

        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(apiClient.getEventById("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeById("promo123")).thenReturn(promoCodeDto);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(modelMapper.map(any(Booking.class), eq(BookingDto.class))).thenReturn(savedBookingDto);

        BookingDto savedBooking = bookingService.createBooking(bookingDto, "event123", "user123", "promo123");

        verify(apiClient, times(1)).getEventById("event123");
        verify(promoCodeClient, times(1)).getPromoCodeById("promo123");
        verify(bookingRepository, times(1)).save(any(Booking.class));

        assertNotNull(savedBooking);
        assertEquals(bookingDto.getNumberOfTickets(), savedBooking.getNumberOfTickets());
        assertEquals(bookingDto.getPrice(), savedBooking.getPrice());
        assertEquals(bookingDto.getStatus(), savedBooking.getStatus());
        assertEquals(eventDto.getEventId(), savedBooking.getEventId());
        assertEquals(promoCodeDto.getPromoCode(), savedBooking.getPromoCode());
    }

    @Test
    public void createInsufficientSeatsTest() {
        eventDto.setSeats(1);
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(apiClient.getEventById("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeById("promo123")).thenReturn(promoCodeDto);

        InsufficientSeatsException insufficientSeatsException = assertThrows(
                InsufficientSeatsException.class,
                () -> bookingService.createBooking(bookingDto, "event123", "user123", "promo123")
        );

        assertEquals("Seat Number Exceeded", insufficientSeatsException.getMessage());
        verify(apiClient, times(1)).getEventById("event123");
    }

    @Test
    public void createBookingIncorrectAmountTest() {
        bookingDto.setPrice(123);
        booking.setPrice(123);
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(apiClient.getEventById("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeById("promo123")).thenReturn(promoCodeDto);

        IncorrectAmountException incorrectAmountException = assertThrows(
                IncorrectAmountException.class,
                () -> bookingService.createBooking(bookingDto, "event123", "user123", "promo123")
        );

        assertEquals("Please enter the correct amount.", incorrectAmountException.getMessage());
        verify(apiClient, times(1)).getEventById("event123");
    }
}
