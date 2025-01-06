package com.booking.controller;

import com.booking.dto.ApiResponseDto;
import com.booking.dto.BookingDto;
import com.booking.service.BookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{userId}/{eventId}/{promoCode}")
    @CircuitBreaker(name = "eventPromocodeBreaker", fallbackMethod = "eventPromocodeFallback")
    @Retry(name = "eventPromocodeRetry", fallbackMethod = "eventPromocodeFallback")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto, @PathVariable String userId,
                                                    @PathVariable String eventId, @PathVariable String promoCode) {
        Logger logger = LoggerFactory.getLogger(BookingController.class);
        logger.info(
                "Booking Dto: {} ", bookingDto
        );
        BookingDto bookingDto1 = bookingService.createBooking(bookingDto, eventId, userId, promoCode);
        return new ResponseEntity<>(bookingDto1, HttpStatus.OK);
    }

    // Fallback method with a new BookingDto instance
    public ResponseEntity<BookingDto> eventPromocodeFallback(BookingDto bookingDto, String userId, String eventId, String promoCode, Throwable e) {
        Logger logger = LoggerFactory.getLogger(BookingController.class);
        logger.error("Service unavailable, falling back to dummy booking.", e);

        // Create a new BookingDto for the fallback response
        BookingDto fallbackBookingDto = new BookingDto();
        fallbackBookingDto.setBookingId(UUID.randomUUID().toString());
        fallbackBookingDto.setEventId(eventId);
        fallbackBookingDto.setPromoCode(promoCode);
        fallbackBookingDto.setEventName("cjhavcugqv");  // Set a dummy event name
        fallbackBookingDto.setUserId(userId);
        fallbackBookingDto.setPrice(456789);  // Dummy price
        fallbackBookingDto.setNumberOfTickets(2);  // Dummy number of tickets

        return new ResponseEntity<>(fallbackBookingDto, HttpStatus.BAD_REQUEST);  // Return the dummy data
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getSingleBooking(@PathVariable String bookingId) {
        BookingDto bookingDto = bookingService.getSingleBooking(bookingId);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookingDto = bookingService.getAllBookings();
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponseDto> deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setMessage("Deleted");
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setSuccess(true);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
