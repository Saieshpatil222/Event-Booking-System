package com.notification.service.impl;

import com.notification.dto.BookingNotificationDto;
import com.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(BookingNotificationDto bookingNotificationDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String subject = "Booking Confirmation: " + bookingNotificationDto.getEventName();
        String body = "Hi " + bookingNotificationDto.getUserName() + ",\n\n" + "Your booking has been confirmed!\n\n" + "Event: " + bookingNotificationDto.getEventName() + "\n" + "Venue: " + bookingNotificationDto.getVenue() + "\n" + "Tickets: " + bookingNotificationDto.getNumberOfTickets() + "\n" + "Total Price: ₹" + bookingNotificationDto.getPrice() + "\n" + "Status: " + bookingNotificationDto.getStatus() + "\n\n" + "Enjoy the event!\nEvent Management Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(bookingNotificationDto.getEmail());
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}
