package com.booking.service;

import com.booking.dto.UserDto;
import com.booking.projectconfig.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/user/internal/{userId}")
    public UserDto getSingleUserForBooking(@PathVariable String userId);

}
