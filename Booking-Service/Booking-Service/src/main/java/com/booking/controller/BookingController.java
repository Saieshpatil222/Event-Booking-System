package com.booking.controller;

import com.booking.dto.ApiResponseDto;
import com.booking.dto.BookingDto;
import com.booking.dto.BookingResponseDto;
import com.booking.service.BookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{userId}/{eventId}/{promoCodeId}")
    @CircuitBreaker(name = "eventPromoCodeBreaker", fallbackMethod = "eventPromoCodeFallback")
    @Retry(name = "eventPromoCodeRetry", fallbackMethod = "eventPromoCodeFallback")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingDto bookingDto, @PathVariable String userId, @PathVariable String eventId, @PathVariable("promoCodeId") String promoCode) {
        BookingDto savedBooking = bookingService.createBooking(bookingDto, eventId, userId, promoCode);
        BookingResponseDto responseDto = BookingResponseDto.builder().message(savedBooking.getStatus()).userName(savedBooking.getUserName()).eventName(savedBooking.getEventName()).venue(savedBooking.getVenue()).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    public ResponseEntity<BookingDto> eventPromoCodeFallback(BookingDto bookingDto, String userId, String eventId, String promoCode, Throwable e) {
        logger.error("Service unavailable, falling back to dummy booking.", e);

        BookingDto fallbackBookingDto = new BookingDto();
        fallbackBookingDto.setBookingId(UUID.randomUUID().toString());
        fallbackBookingDto.setEventId(eventId);
        fallbackBookingDto.setPromoCode(promoCode);
        fallbackBookingDto.setEventName("cjhavcugqv");
        fallbackBookingDto.setUserId(userId);
        fallbackBookingDto.setPrice(456789);
        fallbackBookingDto.setNumberOfTickets(2);

        return new ResponseEntity<>(fallbackBookingDto, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto, @PathVariable String userId, @PathVariable String eventId) {
        BookingDto createdBooking = bookingService.createBookingWithoutPromoCode(bookingDto, eventId, userId);
        return new ResponseEntity<>(createdBooking, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> getSingleBooking(@PathVariable String bookingId) {
        BookingDto bookingDto = bookingService.getSingleBooking(bookingId);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingDto>> getAllBookings(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<BookingDto> bookingDto = bookingService.getAllBookings(offset, pageSize, field);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }


    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NORMAL')")
    public ResponseEntity<ApiResponseDto> deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setMessage("Booking is Canceled successfully Refund will be initiated withing the 2 working days");
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setSuccess(true);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
