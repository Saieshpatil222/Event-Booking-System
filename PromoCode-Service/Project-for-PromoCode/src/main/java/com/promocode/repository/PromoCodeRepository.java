package com.promocode.repository;

import com.promocode.entity.PromoCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends MongoRepository<PromoCode, String> {

    PromoCode findByPromoCode(String promoCode);

}
