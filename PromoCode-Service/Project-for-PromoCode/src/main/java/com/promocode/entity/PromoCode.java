package com.promocode.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class PromoCode {

    @Id
    private String promoCodeId;

    private String promoCode;

    private int discount;
}
