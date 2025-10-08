package com.event.config;

import com.event.service.EventService;
import com.shared.dto.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class KafkaConsumer {

    private final EventService eventService;

    public KafkaConsumer(EventService eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(topics = "booking-confirmed-topic", groupId = "event-service")
    public void consumeBookingConfirmed(BookingEvent events) {
        log.info("Consuming message: {}", events);
        eventService.updateSeatsAfterBooking(events.getEventId(), events.getNumberOfTickets());
    }

}
