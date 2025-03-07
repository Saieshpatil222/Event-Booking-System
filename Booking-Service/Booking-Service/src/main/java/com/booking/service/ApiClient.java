package com.booking.service;

import com.booking.dto.EventDto;
import com.booking.projectconfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EVENT-SERVICE", configuration = FeignClientConfig.class)
public interface ApiClient {

    @GetMapping("/event/{eventId}")
    EventDto getEventById(@PathVariable("eventId") String eventId);

    @PutMapping("/event/{eventId}")
    void updateEvent(@RequestBody EventDto eventDto, @PathVariable String eventId);

}
