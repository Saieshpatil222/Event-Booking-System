package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponseDto {

    private String message;
    private HttpStatus status;
    private boolean success;
    private String venue;
    private String eventName;

    public ApiResponseDto() {

    }


}
