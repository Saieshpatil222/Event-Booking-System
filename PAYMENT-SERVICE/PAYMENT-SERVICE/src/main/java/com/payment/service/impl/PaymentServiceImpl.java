package com.payment.service.impl;

import com.payment.dto.PaymentDto;
import com.payment.entity.Payment;
import com.payment.exception.PaymentNotFoundException;
import com.payment.repository.PaymentRepository;
import com.payment.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(ModelMapper modelMapper, PaymentRepository paymentRepository) {
        this.modelMapper = modelMapper;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, String bookingId, String userId) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setUserId(userId);
        payment.setBookingId(bookingId);
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Booking Id{}", savedPayment.getBookingId());
        return modelMapper.map(savedPayment, PaymentDto.class);
    }

    @Override
    public void deletePayment(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId).orElseThrow(() -> new PaymentNotFoundException("Payment not found for booking ID: " + bookingId));
        paymentRepository.delete(payment);
    }
}
