package com.booking.service;

import com.booking.dto.*;
import com.booking.entity.Booking;
import com.booking.exception.IncorrectAmountException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.repository.BookingRepository;
import com.booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingNotificationService bookingNotificationService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EventClient eventClient;

    @Mock
    private PromoCodeClient promoCodeClient;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private UserClient userClient;

    private BookingDto bookingDto;
    private Booking booking;
    private EventDto eventDto;
    private PromoCodeDto promoCodeDto;


    @BeforeEach
    void setUp() {

        bookingDto = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(200);
        bookingDto.setStatus("CONFIRMED");

        booking = new Booking();
        booking.setNumberOfTickets(2);
        booking.setPrice(200);
        booking.setStatus("CONFIRMED");

        eventDto = new EventDto();
        eventDto.setEventId("event123");
        eventDto.setSeats(10);
        eventDto.setEventPrice(200);

        promoCodeDto = new PromoCodeDto();
        promoCodeDto.setPromoCode("DISCOUNT10");
        promoCodeDto.setDiscount(10);
    }


    @Test
    public void createBooking_withPromoCode_success() {
        // Arrange input DTO
        BookingDto bookingDto = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(100); // Initially sent by client
        bookingDto.setStatus("CONFIRMED");

        // Booking mapped from BookingDto
        Booking booking = new Booking();
        booking.setNumberOfTickets(2);
        booking.setPrice(100);
        booking.setStatus("CONFIRMED");

        // Event
        EventDto eventDto = new EventDto();
        eventDto.setEventId("event123");
        eventDto.setEventPrice(100);
        eventDto.setSeats(100);
        eventDto.setEventName("Test Event");
        eventDto.setVenue("London");

        // Promo code
        PromoCodeDto promoCodeDto = new PromoCodeDto();
        promoCodeDto.setPromoCode("PROMO10");
        promoCodeDto.setDiscount(50);

        // User
        UserDto userDto = new UserDto();
        userDto.setUserName("Test User");
        userDto.setEmailId("test@example.com");

        // Saved booking
        Booking savedBooking = new Booking();
        savedBooking.setBookingId("book123");
        savedBooking.setNumberOfTickets(2);
        savedBooking.setPrice(150); // 2 * 100 - 50
        savedBooking.setStatus("CONFIRMED");
        savedBooking.setVenue("London");
        savedBooking.setPromoCode("PROMO10");
        savedBooking.setEventId("event123");
        savedBooking.setUserId("user123");

        // Result DTO
        BookingDto savedBookingDto = new BookingDto();
        savedBookingDto.setBookingId("book123");
        savedBookingDto.setNumberOfTickets(2);
        savedBookingDto.setPrice(150);
        savedBookingDto.setStatus("CONFIRMED");
        savedBookingDto.setPromoCode("PROMO10");
        savedBookingDto.setEventId("event123");

        // Mocks
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(eventClient.getEventForBooking("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeForBooking("promo123")).thenReturn(promoCodeDto);
        when(userClient.getSingleUserForBooking("user123")).thenReturn(userDto);
        when(modelMapper.map(savedBooking, BookingDto.class)).thenReturn(savedBookingDto);

        // Act
        BookingDto result = bookingService.createBooking(bookingDto, "event123", "user123", "promo123");

        // Assert saved booking and event update
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(eventClient, times(1)).updateEventForBooking(any(EventDto.class), eq("event123"));
        verify(bookingNotificationService, times(1))
                .sendBookingNotification(savedBooking, eventDto, userDto);

        // Final assertions
        assertNotNull(result);
        assertEquals("book123", result.getBookingId());
        assertEquals(2, result.getNumberOfTickets());
        assertEquals(150, result.getPrice());
        assertEquals("CONFIRMED", result.getStatus());
        assertEquals("PROMO10", result.getPromoCode());
        assertEquals("event123", result.getEventId());
    }

    @Test
    public void createBookingWithoutPromoCode_Success() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(100); // per ticket
        bookingDto.setStatus("CONFIRMED");

        EventDto eventDto = new EventDto();
        eventDto.setEventId("event123");
        eventDto.setEventPrice(100);
        eventDto.setSeats(10);
        eventDto.setVenue("London");
        eventDto.setEventName("Test Event");

        UserDto userDto = new UserDto();
        userDto.setUserName("Test User");
        userDto.setEmailId("test@example.com");

        Booking bookingEntity = new Booking();
        bookingEntity.setBookingId("booking123");
        bookingEntity.setNumberOfTickets(2);
        bookingEntity.setPrice(100); // set by service
        bookingEntity.setStatus("CONFIRMED");
        bookingEntity.setVenue("London");
        bookingEntity.setPromoCode("");

        BookingDto savedDto = new BookingDto();
        savedDto.setBookingId("booking123");
        savedDto.setNumberOfTickets(2);
        savedDto.setPrice(200);
        savedDto.setStatus("CONFIRMED");
        savedDto.setVenue("London");
        savedDto.setPromoCode("");

        when(modelMapper.map(any(BookingDto.class), eq(Booking.class))).thenReturn(bookingEntity);
        when(eventClient.getEventForBooking("event123")).thenReturn(eventDto);
        when(userClient.getSingleUserForBooking("user123")).thenReturn(userDto);
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingEntity);
        when(modelMapper.map(any(Booking.class), eq(BookingDto.class))).thenReturn(savedDto);

        BookingDto result = bookingService.createBookingWithoutPromoCode(bookingDto, "event123", "user123");

        ArgumentCaptor<BookingNotificationDto> captor = ArgumentCaptor.forClass(BookingNotificationDto.class);
        verify(notificationClient).sendBookingNotification(captor.capture());
        BookingNotificationDto notification = captor.getValue();

        assertEquals("Test User", notification.getUserName());
        assertEquals("test@example.com", notification.getEmail());
        assertEquals("Test Event", notification.getEventName());
        assertEquals("London", notification.getVenue());

        verify(eventClient).getEventForBooking("event123");
        verify(eventClient).updateEventForBooking(any(EventDto.class), eq("event123"));
        verify(userClient).getSingleUserForBooking("user123");
        verify(bookingRepository).save(any(Booking.class));

        assertNotNull(result);
        assertEquals(2, result.getNumberOfTickets());
        assertEquals(200, result.getPrice()); // total price calculated
        assertEquals("CONFIRMED", result.getStatus());
        assertEquals("London", result.getVenue());
        assertEquals("", result.getPromoCode());
    }


    @Test
    public void createInsufficientSeatsTest() {
        eventDto.setSeats(1);
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(eventClient.getEventForBooking("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeForBooking("promo123")).thenReturn(promoCodeDto);

        InsufficientSeatsException insufficientSeatsException = assertThrows(InsufficientSeatsException.class, () -> bookingService.createBooking(bookingDto, "event123", "user123", "promo123"));

        assertEquals("Seat Number Exceeded", insufficientSeatsException.getMessage());
        verify(eventClient, times(1)).getEventForBooking("event123");
    }

    @Test
    public void createBookingIncorrectAmountTest() {
        bookingDto.setPrice(123);
        booking.setPrice(123);
        when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
        when(eventClient.getEventForBooking("event123")).thenReturn(eventDto);
        when(promoCodeClient.getPromoCodeForBooking("promo123")).thenReturn(promoCodeDto);

        IncorrectAmountException incorrectAmountException = assertThrows(IncorrectAmountException.class, () -> bookingService.createBooking(bookingDto, "event123", "user123", "promo123"));

        assertEquals("Please enter the correct amount.", incorrectAmountException.getMessage());
        verify(eventClient, times(1)).getEventForBooking("event123");
    }

    @Test
    public void deleteBookingTest() {
        String booingId = "12e12ugy";
        Mockito.when(bookingRepository.findById(Mockito.anyString())).thenReturn(Optional.of(booking));
        bookingService.deleteBooking(booingId);
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    public void getAllBookingTest() {

        Booking booking1 = new Booking();
        booking.setNumberOfTickets(2);
        booking.setPrice(200);
        booking.setStatus("CONFIRMED");

        Booking booking2 = new Booking();
        booking.setNumberOfTickets(2);
        booking.setPrice(200);
        booking.setStatus("CONFIRMED");

        BookingDto bookingDto1 = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(200);
        bookingDto.setStatus("CONFIRMED");

        BookingDto bookingDto2 = new BookingDto();
        bookingDto.setNumberOfTickets(2);
        bookingDto.setPrice(200);
        bookingDto.setStatus("CONFIRMED");

        List<Booking> allBookings = Arrays.asList(booking, booking1, booking2);
        List<BookingDto> allBookingsDto = Arrays.asList(bookingDto, bookingDto1, bookingDto2);

        Mockito.when(bookingRepository.findAll()).thenReturn(allBookings);
        Mockito.when(modelMapper.map(booking, BookingDto.class)).thenReturn(bookingDto);
        Mockito.when(modelMapper.map(booking1, BookingDto.class)).thenReturn(bookingDto1);
        Mockito.when(modelMapper.map(booking2, BookingDto.class)).thenReturn(bookingDto2);

        List<BookingDto> bookingDtoList = bookingService.getAllBookings();

        Assertions.assertNotNull(bookingDtoList);
        Assertions.assertEquals(booking.getVenue(), bookingDto.getVenue());
    }

    @Test
    public void getBookingByIdTest() {
        String bookingId = "ugi78y9o";
        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        Mockito.when(modelMapper.map(booking, BookingDto.class)).thenReturn(bookingDto);
        BookingDto singleBookingDto = bookingService.getSingleBooking(bookingId);
        Assertions.assertNotNull(singleBookingDto);
        Assertions.assertEquals(booking.getPrice(), bookingDto.getPrice());
    }
}
