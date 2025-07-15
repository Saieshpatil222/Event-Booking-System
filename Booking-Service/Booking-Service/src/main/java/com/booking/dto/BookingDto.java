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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
