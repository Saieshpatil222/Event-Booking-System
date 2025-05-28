package com.booking.service;

import com.booking.dto.EventDto;
import com.booking.projectconfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EVENT-SERVICE", configuration = FeignClientConfig.class)
public interface EventClient {

    @GetMapping("/event/internal/{eventId}")
    EventDto getEventForBooking(@PathVariable("eventId") String eventId);

    @PutMapping("/event/internal/{eventId}")
    void updateEventForBooking(@RequestBody EventDto eventDto, @PathVariable String eventId);

}
