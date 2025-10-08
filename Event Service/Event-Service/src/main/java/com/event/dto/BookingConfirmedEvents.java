package com.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingConfirmedEvents {
    private String bookingId;
    private String eventId;
    private String userId;
    private int numberOfTickets;
    private int finalPrice;
}
