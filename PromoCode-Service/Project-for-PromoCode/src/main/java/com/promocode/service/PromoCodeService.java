package com.promocode.service;

import com.promocode.dto.PromoCodeDto;

import java.util.List;

public interface PromoCodeService {

    PromoCodeDto createPromoCode(PromoCodeDto promoCodeDto);

    List<PromoCodeDto> getAllPromoCodes();

    void deletePromoCode(String promoCodeId);

    PromoCodeDto getPromoCode(String promoCodeId);

}
