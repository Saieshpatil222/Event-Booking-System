package com.user.exception;

import com.user.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> resourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponseDto responseDto = ApiResponseDto.builder().success(true).status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseDto> resourceNotFoundException(BadApiRequest ex) {
        ApiResponseDto responseDto = ApiResponseDto.builder().success(true).status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
