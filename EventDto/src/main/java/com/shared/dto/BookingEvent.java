package com.shared.dto;

public class BookingEvent {

    private String bookingId;
    private String eventId;
    private String userId;
    private int numberOfTickets;
    private int finalPrice;

    public BookingEvent() {
    }

    public BookingEvent(String bookingId, String userId, String eventId, int numberOfTickets, int finalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.numberOfTickets = numberOfTickets;
        this.finalPrice = finalPrice;
    }

    // Getters & setters
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

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }


}
