package com.notification.controller;

import com.notification.dto.BookingNotificationDto;
import com.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<BookingNotificationDto> sendBookingNotification(@RequestBody BookingNotificationDto bookingNotificationDto) {
        notificationService.send(bookingNotificationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
