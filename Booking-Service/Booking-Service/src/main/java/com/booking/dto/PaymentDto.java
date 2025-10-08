package com.booking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private String bookingId;

    private String paymentId;

    private String userId;

    private int amount;

}
