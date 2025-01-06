package com.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;
}
