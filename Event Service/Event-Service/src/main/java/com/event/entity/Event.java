package com.event.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Document
public class Event {

    @Id
    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;

    private String venue;

    private byte[] eventImage;

    private String eventImageType;

    public Event() {

    }

    public Event(String eventId, String eventName, int eventPrice, Date eventSchedule, int seats, String venue, byte[] eventImage, String eventImageType) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
        this.eventSchedule = eventSchedule;
        this.seats = seats;
        this.venue = venue;
        this.eventImage = eventImage;
        this.eventImageType = eventImageType;
    }

    public String getEventImageType() {
        return eventImageType;
    }

    public void setEventImageType(String eventImageType) {
        this.eventImageType = eventImageType;
    }

    public byte[] getEventImage() {
        return eventImage;
    }

    public void setEventImage(byte[] eventImage) {
        this.eventImage = eventImage;
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

    public int getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(int eventPrice) {
        this.eventPrice = eventPrice;
    }

    public Date getEventSchedule() {
        return eventSchedule;
    }

    public void setEventSchedule(Date eventSchedule) {
        this.eventSchedule = eventSchedule;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenue() {
        return venue;
    }
}
