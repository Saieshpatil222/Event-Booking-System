package com.user.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDto {

    private String message;

    private HttpStatus status;

    private boolean success;

}
