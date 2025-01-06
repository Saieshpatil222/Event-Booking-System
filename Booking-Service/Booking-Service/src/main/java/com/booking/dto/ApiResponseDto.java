package com.booking.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {

    private String message;

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

    private HttpStatus status;

    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
