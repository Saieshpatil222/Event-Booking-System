package com.event.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Getter
@Builder
@Setter
public class EventDto {

    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;

    private String venue;

    private byte[] eventImage;

    private String eventImageType;

    public EventDto() {

    }

    public EventDto(String eventId, String eventName, int eventPrice, Date eventSchedule, int seats, String venue, byte[] eventImage, String eventImageType) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
        this.eventSchedule = eventSchedule;
        this.seats = seats;
        this.venue = venue;
        this.eventImage = eventImage;
        this.eventImageType = eventImageType;
    }

    public void setEventImage(byte[] eventImage) {
        this.eventImage = eventImage;
    }

    public void setEventImageType(String eventImageType) {
        this.eventImageType = eventImageType;
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

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
