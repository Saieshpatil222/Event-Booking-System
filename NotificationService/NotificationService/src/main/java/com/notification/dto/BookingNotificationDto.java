package com.notification.dto;

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

    private String status;

    private String venue;

    private String email;

    private String userName;

}
