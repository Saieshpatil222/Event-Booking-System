package com.booking.dto;

import lombok.*;

@Builder
public class PromoCodeDto {

    private String promoCodeId;

    private String promoCode;

    private int discount;

    public PromoCodeDto() {

    }

    public PromoCodeDto(String promoCodeId, String promoCode, int discount) {
        this.promoCodeId = promoCodeId;
        this.promoCode = promoCode;
        this.discount = discount;
    }

    public String getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(String promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }


}
