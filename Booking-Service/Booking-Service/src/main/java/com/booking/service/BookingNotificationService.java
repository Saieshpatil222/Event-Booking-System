package com.booking.service;

import com.booking.dto.EventDto;
import com.booking.dto.UserDto;
import com.booking.entity.Booking;
import org.springframework.scheduling.annotation.Async;

public interface BookingNotificationService {

    @Async("taskExecutor")
    void sendBookingNotification(Booking booking, EventDto eventDto, UserDto userDto);


}
