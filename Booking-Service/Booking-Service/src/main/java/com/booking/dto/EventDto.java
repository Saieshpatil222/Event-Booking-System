package com.booking.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {

    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;


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


}
