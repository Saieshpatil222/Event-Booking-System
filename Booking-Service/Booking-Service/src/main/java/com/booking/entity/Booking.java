package com.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    private String bookingId;

    private String userName;

    private String userId;

    private String eventId;

    private String eventName;

    private int numberOfTickets;

    private int price;

    private String status;

    private String promoCode;

    private String venue;

    public Booking() {

    }


}
