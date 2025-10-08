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

    public String getEventName() {
        return eventName;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getVenue() {
        return venue;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    private String venue;

    private String email;

    private String userName;

}
