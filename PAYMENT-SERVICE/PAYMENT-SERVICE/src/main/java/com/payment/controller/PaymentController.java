package com.payment.controller;

import com.payment.dto.ApiResponseDto;
import com.payment.dto.PaymentDto;
import com.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{bookingId}/{userId}")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto, @PathVariable String bookingId, @PathVariable String userId) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto, bookingId, userId);
        return ResponseEntity.ok(createdPayment);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponseDto> deletePayment(@PathVariable String bookingId) {
        paymentService.deletePayment(bookingId);
        ApiResponseDto apiResponse = ApiResponseDto.builder().httpStatus(HttpStatus.OK).success(true).message("Payment Deleted").build();
        return ResponseEntity.ok(apiResponse);
    }

}

