package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingNotificationDto {

    private String eventName;

    private int numberOfTickets;

    private int price;

    private String email;

    private String status;

    private String venue;

    private String userName;

}
