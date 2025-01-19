package com.promocode.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeDto {


    private String promoCodeId;

    private String promoCode;

    private int discount;

}
