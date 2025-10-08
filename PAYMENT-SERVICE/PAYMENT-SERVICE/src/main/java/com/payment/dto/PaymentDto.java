package com.payment.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {


    private String paymentId;


    private int amount;

}
