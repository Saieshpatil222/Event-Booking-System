package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class BookingDto {

    private String bookingId;

    private String userId;

    private String venue;

    private String eventName;

    private int numberOfTickets;

    private String userName;

    private int price;

    private boolean isPaid;

    private String status;

    private String eventId;

    private String promoCode;

    public BookingDto() {

    }

    public BookingDto(String bookingId, String userId, String venue, String eventName, int numberOfTickets, int price, String status, String eventId, String promoCode) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.venue = venue;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
        this.eventId = eventId;
        this.promoCode = promoCode;
    }

}
