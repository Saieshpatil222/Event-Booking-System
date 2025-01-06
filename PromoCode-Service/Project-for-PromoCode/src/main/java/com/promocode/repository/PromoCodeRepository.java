package com.promocode.repository;

import com.promocode.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {

    PromoCode findByPromoCode(String promoCode);

}
