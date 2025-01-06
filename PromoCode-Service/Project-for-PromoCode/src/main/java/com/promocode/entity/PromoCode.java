package com.promocode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PromoCode {

    @Id
    private String promocodeId;

    private String promoCode;

    private int discount;
}
