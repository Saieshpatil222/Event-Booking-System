package com.event.controller;

import com.event.dto.ApiResponseDto;
import com.event.dto.EventDto;
import com.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto eventDto1 = eventService.createEvent(eventDto);
        return new ResponseEntity<>(eventDto1, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> eventDtos = eventService.getAllEvents();
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventDto> getEventById(@PathVariable String eventId) {
        EventDto eventDto = eventService.getSingleEvent(eventId);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto> deleteEvent(@PathVariable("eventId") String eventId) {
        eventService.deleteEvent(eventId);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().message("Event Deleted Successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable String eventId, @RequestBody EventDto eventDto) {
        EventDto eventDto1 = eventService.updateEvent(eventId, eventDto);
        return new ResponseEntity<>(eventDto1, HttpStatus.OK);
    }

    @PostMapping("/upload-image/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDto> uploadImage(@PathVariable String eventId, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        EventDto eventDto = eventService.uploadImage(eventId, multipartFile);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }


    @GetMapping("/image/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> getEventImage(@PathVariable String eventId) {
        EventDto eventDTO = eventService.getEventImage(eventId);
        if (eventDTO.getEventImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(eventDTO.getEventImageType()))
                .body(eventDTO.getEventImage());
    }
}
