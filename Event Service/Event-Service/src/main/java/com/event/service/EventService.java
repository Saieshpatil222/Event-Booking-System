package com.event.service;

import com.event.dto.EventDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {

    EventDto createEvent(EventDto eventDto);

    List<EventDto> getAllEvents();

    EventDto getSingleEvent(String eventId);

    void deleteEvent(String eventId);

    EventDto updateEvent(String eventId, EventDto eventDto);

    EventDto uploadImage(String eventId, MultipartFile multipartFile) throws IOException;

    EventDto getEventImage(String eventId);

}
