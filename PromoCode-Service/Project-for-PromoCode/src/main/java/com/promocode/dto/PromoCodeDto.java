package com.promocode.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeDto {


    private String promocodeId;

    private String promoCode;

    private int discount;

}
