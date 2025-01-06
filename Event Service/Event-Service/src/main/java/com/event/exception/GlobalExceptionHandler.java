package com.event.exception;

import com.event.dto.ApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> bookingNotFoundExceptionHandler(ResourceNotFoundException ex) {
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

        logger.info("Exception Handler invoked!!");
        ApiResponseDto responseDto = new ApiResponseDto();
        responseDto.setSuccess(true);
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setMessage("Event Not Found");
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
