package com.notification.service;

import com.notification.dto.BookingNotificationDto;

public interface NotificationService {

    void send(BookingNotificationDto bookingNotificationDto);

}
