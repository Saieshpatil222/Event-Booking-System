package com.payment.service;

import com.payment.dto.PaymentDto;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto, String bookingId, String userId);

    void deletePayment(String bookingId);

}
