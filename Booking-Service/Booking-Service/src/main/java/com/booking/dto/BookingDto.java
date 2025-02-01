package com.booking.dto;

public class BookingDto {

    private String bookingId;

    private String userId;

    private String address;

    private String eventName;

    private int numberOfTickets;

    private int price;

    private String status;

    private String eventId;

    private String promoCode;

    public BookingDto() {

    }

    public BookingDto(String bookingId, String userId, String eventId, String address, String eventName, int numberOfTickets, int price, String status, String promoCode) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.address = address;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
        this.status = status;
        this.promoCode = promoCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "BookingDto{" +
                "bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", numberOfTickets=" + numberOfTickets +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", promoCode='" + promoCode + '\'' +
                '}';
    }
}
