package com.event.service;

import com.event.dto.EventDto;
import com.event.entity.Event;
import com.event.repository.EventRepository;
import com.event.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private ModelMapper modelMapper;

    Calendar calendar = Calendar.getInstance();

    Date eventDate = calendar.getTime();

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void createEvent() {
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").eventId("123").build();
        Event event = Event.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").eventId("123").build();
        Event saved = Event.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").eventId("123").build();
        Mockito.when(modelMapper.map(any(EventDto.class), any(Class.class))).thenReturn(saved);
        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(saved);
        Mockito.when(modelMapper.map(any(Event.class), any(Class.class))).thenReturn(eventDto);
        EventDto eventDto1 = eventService.createEvent(eventDto);
        Assertions.assertNotNull(eventDto1);
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void getEvent() {
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();
        Event event = Event.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();
        String eventId = "122334";
        Mockito.when(eventRepository.findById(Mockito.anyString())).thenReturn(Optional.of(event));
        Mockito.when(modelMapper.map(any(Event.class), any(Class.class))).thenReturn(eventDto);
        EventDto event1 = eventService.getSingleEvent(eventId);
        Assertions.assertNotNull(event1);
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void getAllEvents() {
        Event event1 = Event.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();
        Event event2 = Event.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();
        Event event3 = Event.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();

        EventDto eventDto1 = EventDto.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();
        EventDto eventDto2 = EventDto.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();
        EventDto eventDto3 = EventDto.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).venue("sdfgh").seats(88).build();

        List<Event> eventsList = Arrays.asList(event1, event2, event3);
        List<EventDto> eventDtosList = Arrays.asList(eventDto1, eventDto2, eventDto3);

        Mockito.when(eventRepository.findAll()).thenReturn(eventsList);
        Mockito.when(modelMapper.map(event1, EventDto.class)).thenReturn(eventDto1);
        Mockito.when(modelMapper.map(event2, EventDto.class)).thenReturn(eventDto2);
        Mockito.when(modelMapper.map(event3, EventDto.class)).thenReturn(eventDto3);

        List<EventDto> eventDtos = eventService.getAllEvents();
        Assertions.assertNotNull(eventDtos);
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void deleteEvent() {
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();
        Event event = Event.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();
        String eventId = "122334";

        Mockito.when(eventRepository.findById(Mockito.anyString())).thenReturn(Optional.of(event));
        eventService.deleteEvent(eventId);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void updateEventTest() {
        String eventId = "Chsj";
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();
        Event event = Event.builder().eventName("ABC").eventPrice(1234).eventSchedule(eventDate).venue("xyz").build();

        Mockito.when(eventRepository.findById(Mockito.anyString())).thenReturn(Optional.of(event));
        Mockito.when(modelMapper.map(event, EventDto.class)).thenReturn(eventDto);
        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(event);
        EventDto updatedEventDto = eventService.updateEvent(eventId, eventDto);
        Assertions.assertNotNull(updatedEventDto);
    }

}
