package com.event.controller;

import com.event.dto.EventDto;
import com.event.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    Calendar calendar = Calendar.getInstance();

    Date date = calendar.getTime();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createEventTest() throws Exception {
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(200).seats(2).venue("XYZ").eventSchedule(date).build();
        EventDto savedEvent = EventDto.builder().eventName("ABC").eventPrice(200).seats(2).venue("XYZ").eventSchedule(date).build();

        Mockito.when(eventService.createEvent(any(EventDto.class))).thenReturn(savedEvent);

        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eventName").value(savedEvent.getEventName()))
                .andExpect(jsonPath("$.seats").value(savedEvent.getSeats()))
                .andExpect(jsonPath("$.eventPrice").value(savedEvent.getEventPrice()))
                .andDo(print());

        Mockito.verify(eventService, times(1)).createEvent(any(EventDto.class));
    }

    @Test
    public void getEventTest() throws Exception {
        String eventId = "event123";
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(200).seats(2).venue("XYZ").eventSchedule(date).build();

        Mockito.when(eventService.getSingleEvent(eventId)).thenReturn(eventDto);
        mockMvc.perform(get("/event/{eventId}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value(eventDto.getEventName()))
                .andExpect(jsonPath("$.seats").value(eventDto.getSeats()))
                .andExpect(jsonPath("$.eventPrice").value(eventDto.getEventPrice()))
                .andDo(print());

        Mockito.verify(eventService, times(1)).getSingleEvent(anyString());
    }

    @Test
    public void getAllEventsTest() throws Exception {
        EventDto eventDto1 = EventDto.builder().eventName("ABC").eventPrice(200).seats(2).venue("XYZ").eventSchedule(date).build();
        EventDto eventDto2 = EventDto.builder().eventName("XYZ").eventPrice(300).seats(3).venue("ABC").eventSchedule(date).build();
        EventDto eventDto3 = EventDto.builder().eventName("MNC").eventPrice(400).seats(4).venue("FDS").eventSchedule(date).build();

        List<EventDto> eventDtoList = Arrays.asList(eventDto1, eventDto2, eventDto3);

        Mockito.when(eventService.getAllEvents()).thenReturn(eventDtoList);

        mockMvc.perform(get("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value(eventDto1.getEventName()))
                .andExpect(jsonPath("$[0].seats").value(eventDto1.getSeats()))
                .andExpect(jsonPath("$[1].eventName").value(eventDto2.getEventName()))
                .andExpect(jsonPath("$[1].seats").value(eventDto2.getSeats()))
                .andExpect(jsonPath("$[2].eventName").value(eventDto3.getEventName()))
                .andExpect(jsonPath("$[2].seats").value(eventDto3.getSeats()))
                .andDo(print());

        Mockito.verify(eventService, times(1)).getAllEvents();
    }


    @Test
    public void updateUserTest() throws Exception {
        String eventId = "event123";
        EventDto eventDto = EventDto.builder().eventName("ABC").eventPrice(200).seats(2).venue("XYZ").eventSchedule(date).build();
        EventDto updatedEvent = EventDto.builder().eventName("ABC").eventPrice(2000).seats(4).venue("XYZA").eventSchedule(date).build();

        Mockito.when(eventService.updateEvent(eq(eventId), any(EventDto.class))).thenReturn(updatedEvent);

        mockMvc.perform(put("/event/{eventId}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("normal").roles("NORMAL"))
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("ABC"))
                .andExpect(jsonPath("$.eventPrice").value("2000"))
                .andExpect(jsonPath("$.seats").value("4"))
                .andExpect(status().isOk())
                .andDo(print());
        verify(eventService, times(1)).updateEvent(eq(eventId), any(EventDto.class));
    }
}
