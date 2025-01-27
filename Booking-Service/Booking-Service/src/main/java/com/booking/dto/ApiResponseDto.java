package com.booking.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ApiResponseDto {

    private String message;
    private HttpStatus status;
    private boolean success;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
