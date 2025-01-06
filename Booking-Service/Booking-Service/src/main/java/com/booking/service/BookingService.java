package com.booking.service;

import com.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto, String eventId, String userId, String promoCode);

    void deleteBooking(String bookingId);

    BookingDto getSingleBooking(String bookingId);

    List<BookingDto> getAllBookings();
}
