package com.booking.controller;

import com.booking.dto.BookingDto;
import com.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createBookingTest() throws Exception {
        String userId = "user123";
        String eventId = "event123";
        String promoCode = "promo123";
        BookingDto bookingDto = new BookingDto();
        bookingDto.setUserId(userId);
        bookingDto.setEventName("Cold Play");
        bookingDto.setEventId(eventId);
        bookingDto.setPromoCode(promoCode);

        BookingDto savedBooking = new BookingDto();
        savedBooking.setBookingId("12345");
        savedBooking.setUserId(userId);
        savedBooking.setEventName("Cold Play");
        savedBooking.setEventId(eventId);
        savedBooking.setPromoCode(promoCode);
        when(bookingService.createBooking(any(BookingDto.class), anyString(), anyString(), anyString())).thenReturn(savedBooking);

        mockMvc.perform(post("/booking/{userId}/{eventId}/{promoCodeId}", userId, eventId, promoCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))//writeValueAsString(bookingDto) converts the bookingDto Java object into a JSON string.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedBooking.getUserId()))
                .andExpect(jsonPath("$.eventName").value(savedBooking.getEventName()))
                .andDo(print());

        verify(bookingService, times(1)).createBooking(any(BookingDto.class), anyString(), anyString(), anyString());
    }

    @Test
    public void createBookingWithoutPromoCodeTest() throws Exception {
        String userId = "user123";
        String eventId = "event123";

        BookingDto bookingDto = new BookingDto();
        bookingDto.setUserId(userId);
        bookingDto.setEventName("Cold Play");
        bookingDto.setEventId(eventId);

        BookingDto savedBooking = new BookingDto();
        savedBooking.setBookingId("12345");
        savedBooking.setUserId(userId);
        savedBooking.setEventName("Cold Play");
        savedBooking.setEventId(eventId);

        when(bookingService.createBookingWithoutPromoCode(any(BookingDto.class), anyString(), anyString())).thenReturn(savedBooking);

        mockMvc.perform(post("/booking/{userId}/{eventId}", userId, eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedBooking.getUserId()))
                .andExpect(jsonPath("$.eventId").value(savedBooking.getEventId()))
                .andExpect(jsonPath("$.eventName").value(savedBooking.getEventName()))
                .andExpect(jsonPath("$.bookingId").value(savedBooking.getBookingId()))
                .andDo(print());

        verify(bookingService, times(1)).createBookingWithoutPromoCode(any(BookingDto.class), anyString(), anyString());
    }

    @Test
    public void getSingleBookingTest() throws Exception {
        String bookingId = "booking123";
        BookingDto bookingDto = new BookingDto();
        bookingDto.setUserId("user123");
        bookingDto.setEventId("event123");
        bookingDto.setPromoCode("promo123");
        bookingDto.setBookingId("booking123");
        bookingDto.setEventName("XYZ");

        when(bookingService.getSingleBooking(bookingId)).thenReturn(bookingDto);

        mockMvc.perform(get("/booking/{bookingId}", bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(bookingDto.getUserId()))
                .andExpect(jsonPath("$.eventId").value(bookingDto.getEventId()))
                .andExpect(jsonPath("$.eventName").value(bookingDto.getEventName()))
                .andExpect(jsonPath("$.bookingId").value(bookingDto.getBookingId()))
                .andDo(print());

        verify(bookingService, times(1)).getSingleBooking(bookingId);
    }

    @Test
    public void getAllUsers() throws Exception {
        BookingDto bookingDto1 = new BookingDto();
        bookingDto1.setUserId("user123");
        bookingDto1.setEventId("event123");
        bookingDto1.setPromoCode("promo123");
        bookingDto1.setBookingId("booking123");
        bookingDto1.setEventName("XYZ");

        BookingDto bookingDto2 = new BookingDto();
        bookingDto2.setUserId("user1234");
        bookingDto2.setEventId("event123");
        bookingDto2.setPromoCode("promo123");
        bookingDto2.setBookingId("booking123");
        bookingDto2.setEventName("XYZ");

        BookingDto bookingDto3 = new BookingDto();
        bookingDto3.setUserId("user12345");
        bookingDto3.setEventId("event123");
        bookingDto3.setPromoCode("promo123");
        bookingDto3.setBookingId("booking123");
        bookingDto3.setEventName("XYZ");

        List<BookingDto> bookingDtoList = Arrays.asList(bookingDto1, bookingDto2, bookingDto3);

        Mockito.when(bookingService.getAllBookings()).thenReturn(bookingDtoList);

        mockMvc.perform(get("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("user123"))
                .andExpect(jsonPath("$[0].eventId").value(bookingDto1.getEventId()))
                .andExpect(jsonPath("$[1].userId").value(bookingDto2.getUserId()))
                .andExpect(jsonPath("$[1].eventId").value(bookingDto2.getEventId()))
                .andDo(print());

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    public void deleteBookingTest() throws Exception {
        String bookingId = "booking123";
        BookingDto bookingDto1 = new BookingDto();
        bookingDto1.setUserId("user123");
        bookingDto1.setEventId("event123");
        bookingDto1.setPromoCode("promo123");
        bookingDto1.setBookingId("booking123");
        bookingDto1.setEventName("XYZ");
        bookingDto1.setPrice(564);
        bookingDto1.setNumberOfTickets(2);

        Mockito.doNothing().when(bookingService).deleteBooking(bookingId);
        mockMvc.perform(delete("/booking/{bookingId}", bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto1)))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).deleteBooking(bookingId);
    }
}
