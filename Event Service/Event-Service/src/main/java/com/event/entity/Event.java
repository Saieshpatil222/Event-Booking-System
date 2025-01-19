package com.event.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Event {

    @Id
    private String eventId;

    private String eventName;

    private int eventPrice;

    private Date eventSchedule;

    private int seats;

    private String address;
}
