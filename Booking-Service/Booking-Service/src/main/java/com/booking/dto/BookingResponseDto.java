package com.booking.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingResponseDto {

    // Getters
    private String message;
    private String venue;
    private String eventName;
    private String userName;
    private Date eventDate;

}
