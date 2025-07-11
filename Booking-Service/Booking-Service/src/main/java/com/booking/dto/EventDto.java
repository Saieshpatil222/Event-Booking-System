package com.booking.dto;

import lombok.*;

import java.util.Date;

@Getter
@Builder
public class EventDto {

    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;

    private String venue;

    public EventDto() {

    }

    public EventDto(String eventId, String eventName, int eventPrice, Date eventSchedule, int seats, String venue) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
        this.eventSchedule = eventSchedule;
        this.seats = seats;
        this.venue = venue;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventPrice(int eventPrice) {
        this.eventPrice = eventPrice;
    }

    public void setEventSchedule(Date eventSchedule) {
        this.eventSchedule = eventSchedule;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setVenue(String address) {
        this.venue = address;
    }


}
