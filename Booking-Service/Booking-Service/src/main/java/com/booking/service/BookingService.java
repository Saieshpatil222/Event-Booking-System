package com.booking.service;

import com.booking.dto.ApiResponseDto;
import com.booking.dto.BookingDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto, String eventId, String userId, String promoCode);

    BookingDto createBookingWithoutPromoCode(BookingDto bookingDto, String eventId, String userId);

    void deleteBooking(String bookingId);

    BookingDto getSingleBooking(String bookingId);

    Page<BookingDto> getAllBookings(int offSet, int pageNumber, String field);
}
