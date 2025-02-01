package com.booking.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.print.Book;

@Builder
@Document
public class Booking {

    @Id
    private String bookingId;

    private String userId;

    private String eventId;

    private String eventName;

    private int numberOfTickets;

    private int price;

    private String status;

    private String promoCode;

    private String address;

    public Booking() {

    }

    public Booking(String bookingId, String userId, String eventId, String eventName, int numberOfTickets, int price, String status, String promoCode, String address) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
        this.status = status;
        this.promoCode = promoCode;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public String getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
