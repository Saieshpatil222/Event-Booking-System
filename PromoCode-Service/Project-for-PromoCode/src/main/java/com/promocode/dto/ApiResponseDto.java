package com.promocode.dto;

import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDto {

    private String message;

    private HttpStatus status;

    private boolean success;

}
