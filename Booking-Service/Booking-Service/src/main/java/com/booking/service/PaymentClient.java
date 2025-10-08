package com.booking.service;

import com.booking.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payment/{bookingId}/{userId}")
    PaymentDto createPayment(@RequestBody PaymentDto paymentDto, @PathVariable String bookingId, @PathVariable String userId);

    @DeleteMapping("/payment/{bookingId}")
    void deletePayment(@PathVariable String bookingId);
}
