package com.event.dto;

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

}
