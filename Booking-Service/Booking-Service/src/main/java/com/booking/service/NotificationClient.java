package com.booking.service;

import com.booking.dto.BookingNotificationDto;
import com.booking.projectconfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NotificationService", configuration = FeignClientConfig.class)
public interface NotificationClient {

    @PostMapping("/notification")
    void sendBookingNotification(@RequestBody BookingNotificationDto bookingNotificationDto);

}
