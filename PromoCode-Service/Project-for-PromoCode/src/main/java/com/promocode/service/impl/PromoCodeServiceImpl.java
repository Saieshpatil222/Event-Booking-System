package com.promocode.service.impl;

import com.promocode.dto.PromoCodeDto;
import com.promocode.entity.PromoCode;
import com.promocode.exception.ResourceNotFoundException;
import com.promocode.repository.PromoCodeRepository;
import com.promocode.service.PromoCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PromoCodeDto createPromoCode(PromoCodeDto promoCodeDto) {
        PromoCode promoCode = modelMapper.map(promoCodeDto, PromoCode.class);
        promoCode.setPromoCodeId(UUID.randomUUID().toString());
        PromoCode promoCode1 = promoCodeRepository.save(promoCode);
        PromoCodeDto promoCodeDto1 = modelMapper.map(promoCode1, PromoCodeDto.class);
        return promoCodeDto1;
    }

    @Override
    public List<PromoCodeDto> getAllPromoCodes() {
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        return promoCodes.stream().map(promoCode -> modelMapper.map(promoCode, PromoCodeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePromoCode(String promoCodeId) {
        PromoCode code = promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new ResourceNotFoundException("PromoCode Not Found"));
        promoCodeRepository.delete(code);
    }

    @Override
    public PromoCodeDto getPromoCode(String promoCodeId) {
        PromoCode promoCode1 = promoCodeRepository.findById(promoCodeId).orElseThrow(() -> new ResourceNotFoundException("PromoCode Not Found " + promoCodeId));
        //promoCode1.setPromocodeId(promoCode1.getPromocodeId());
        return modelMapper.map(promoCode1, PromoCodeDto.class);
    }
}
