package com.event.service;

import com.event.dto.EventDto;
import com.event.entity.Event;
import com.event.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EventServiceTest {

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    @Mock
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    Event event;

    Calendar calendar = Calendar.getInstance();

    Date eventDate = calendar.getTime();

    @BeforeEach
    public void init() {
        event = Event.builder().eventName("FGH").eventPrice(80).eventSchedule(eventDate).seats(90).address("ghj").build();
    }

    @Test
    public void createEvent() {
        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(event);
        EventDto eventDto1 = eventService.createEvent(modelMapper.map(event, EventDto.class));
        Assertions.assertNotNull(eventDto1);
    }

    @Test
    public void getEvent() {
        String eventId = "122334";
        Mockito.when(eventRepository.findById(Mockito.any())).thenReturn(Optional.of(event));
        EventDto event1 = eventService.getSingleEvent(eventId);
        Assertions.assertNotNull(event1);
    }

    @Test
    public void getAllEvents() {
        Event event1 = Event.builder().eventName("fghj").eventSchedule(eventDate).eventPrice(78).address("sdfgh").seats(88).build();
        Event event2 = Event.builder().eventName("7yt6y").eventSchedule(eventDate).eventPrice(56789).address("098u7ytgfde").seats(65).build();
        List<Event> events = Arrays.asList(event, event1, event2);
        Mockito.when(eventRepository.findAll()).thenReturn(events);
        List<EventDto> eventDtos = eventService.getAllEvents();
        Assertions.assertNotNull(eventDtos);
    }

    @Test
    public void deleteEvent() {
        String eventId = "87tgyuhgbi";
        Mockito.when(eventRepository.findById(Mockito.any())).thenReturn(Optional.of(event));
        eventService.deleteEvent(eventId);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    public void updateEventTest() {
        String eventId = "Chsj";
        EventDto event2 = EventDto.builder().eventName("7yt6y").eventSchedule(eventDate).eventPrice(56789).address("098u7ytgfde").seats(65).build();
        Mockito.when(eventRepository.findById(Mockito.any())).thenReturn(Optional.of(event));
        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(event);
        EventDto eventDto = eventService.updateEvent(eventId, event2);
        Assertions.assertNotNull(eventDto);
    }

}
