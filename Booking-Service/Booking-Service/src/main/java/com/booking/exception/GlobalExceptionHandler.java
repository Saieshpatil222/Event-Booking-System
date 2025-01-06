package com.booking.exception;

import com.booking.dto.ApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ApiResponseDto> bookingNotFoundExceptionHandler(BookingNotFoundException ex) {
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

        logger.info("Exception Handler invoked!!");
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setSuccess(true);
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setMessage("Booking Not Found");
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectAmountException.class)
    public ResponseEntity<ApiResponseDto> incorrectAmountExceptionHandler(BookingNotFoundException ex) {
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

        logger.info("Exception Handler invoked!!");
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setSuccess(false);
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        responseDto.setMessage("Incorrect Amount");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(InsufficientSeatsException.class)
    public ResponseEntity<ApiResponseDto> insufficientSeatsExceptionHandler(BookingNotFoundException ex) {
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

        logger.info("Exception Handler invoked!!");
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setSuccess(false);
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        responseDto.setMessage("Insufficient Seats");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
