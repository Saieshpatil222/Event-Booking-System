package com.user.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
public class ApiResponseDto {

    private String message;

    private HttpStatus status;

    private boolean success;

}
