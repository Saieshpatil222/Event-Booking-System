package com.event.service.impl;

import com.event.dto.EventDto;
import com.event.entity.Event;
import com.event.exception.ResourceNotFoundException;
import com.event.repository.EventRepository;
import com.event.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        event.setEventId(UUID.randomUUID().toString());
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventDto.class);
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream().map(events -> modelMapper.map(events, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    public EventDto getSingleEvent(String eventId) {
        Event singleEvent = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event Not Found"));
        return modelMapper.map(singleEvent, EventDto.class);
    }

    @Override
    public void deleteEvent(String eventId) {
        Event deletdEvent = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event Not Found With " + eventId));
        eventRepository.delete(deletdEvent);
    }

    @Override
    public EventDto updateEvent(String eventId, EventDto eventDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event Not Found with Given Id " + eventId));
        event.setEventPrice(eventDto.getEventPrice());
        event.setSeats(eventDto.getSeats());
        event.setVenue(eventDto.getVenue());
        Event updatedEvent = eventRepository.save(event);
        return modelMapper.map(updatedEvent, EventDto.class);
    }

    @Override
    public EventDto uploadImage(String eventId, MultipartFile multipartFile) throws IOException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event Not Found With Given Id"));
        byte[] imageBytes = multipartFile.getBytes();
        event.setEventImage(imageBytes);
        event.setEventImageType(multipartFile.getContentType());
        Event savedEventImage = eventRepository.save(event);
        return modelMapper.map(savedEventImage, EventDto.class);
    }

    @Override
    public EventDto getEventImage(String eventId) {
        Event eventImage = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        return modelMapper.map(eventImage, EventDto.class);
    }

}
