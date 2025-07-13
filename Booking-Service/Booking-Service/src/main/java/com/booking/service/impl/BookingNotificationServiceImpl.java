package com.booking.service.impl;

import com.booking.dto.BookingNotificationDto;
import com.booking.dto.EventDto;
import com.booking.dto.UserDto;
import com.booking.entity.Booking;
import com.booking.service.BookingNotificationService;
import com.booking.service.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingNotificationServiceImpl implements BookingNotificationService {

    @Autowired
    private NotificationClient notificationClient;

    @Override
    public void sendBookingNotification(Booking booking, EventDto eventDto, UserDto userDto) {
        System.out.println("Thread name:" + Thread.currentThread());
        BookingNotificationDto notificationDto = new BookingNotificationDto();
        notificationDto.setEventName(eventDto.getEventName());
        notificationDto.setEmail(userDto.getEmailId());
        notificationDto.setNumberOfTickets(booking.getNumberOfTickets());
        notificationDto.setPrice(booking.getPrice());
        notificationDto.setUserName(userDto.getUserName());
        notificationDto.setStatus(booking.getStatus());
        notificationDto.setVenue(booking.getVenue());
        notificationClient.sendBookingNotification(notificationDto);
    }
}

