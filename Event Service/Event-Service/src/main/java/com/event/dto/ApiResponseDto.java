package com.event.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
public class ApiResponseDto {

    private String message;

    private HttpStatus status;

    private boolean success;

    public ApiResponseDto() {
    }

    public ApiResponseDto(String message, HttpStatus status, boolean success) {
        this.message = message;
        this.status = status;
        this.success = success;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
