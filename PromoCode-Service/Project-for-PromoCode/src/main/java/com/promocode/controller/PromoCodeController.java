package com.promocode.controller;

import com.promocode.dto.ApiResponseDto;
import com.promocode.dto.PromoCodeDto;
import com.promocode.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promocode")
public class PromoCodeController {

    @Autowired
    private PromoCodeService promoCodeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromoCodeDto> createPromoCode(@RequestBody PromoCodeDto promoCodeDto) {
        PromoCodeDto promoCodeDto1 = promoCodeService.createPromoCode(promoCodeDto);
        return new ResponseEntity<>(promoCodeDto1, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PromoCodeDto>> getAllPromoCodes() {
        List<PromoCodeDto> promoCode = promoCodeService.getAllPromoCodes();
        return new ResponseEntity<>(promoCode, HttpStatus.FOUND);
    }

    @GetMapping("/{promoCode}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_NORMAL')")
    public ResponseEntity<PromoCodeDto> getPromoCode(@PathVariable String promoCode) {
        PromoCodeDto codeDto = promoCodeService.getPromoCode(promoCode);
        return new ResponseEntity<>(codeDto, HttpStatus.OK);
    }

    @DeleteMapping("/{promoCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto> deletePromoCode(@PathVariable("promoCode") String promoCodeId) {
        promoCodeService.deletePromoCode(promoCodeId);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().message("PromoCode Deleted Successfully")
                .status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

    }

}
