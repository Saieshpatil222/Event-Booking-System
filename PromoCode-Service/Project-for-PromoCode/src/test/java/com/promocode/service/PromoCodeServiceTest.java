package com.promocode.service;

import com.promocode.dto.PromoCodeDto;
import com.promocode.entity.PromoCode;
import com.promocode.repository.PromoCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PromoCodeServiceTest {

    @Mock
    @Autowired
    private PromoCodeService promoCodeService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private PromoCodeRepository promoCodeRepository;

    PromoCode promoCode;

    @BeforeEach
    public void init() {
        promoCode = PromoCode.builder().discount(56).promoCode("adf").build();
    }

    @Test
    public void createPromoCodeTest() {
        Mockito.when(promoCodeRepository.save(Mockito.any())).thenReturn(promoCode);
        PromoCodeDto promoCodeDto1 = promoCodeService.createPromoCode(modelMapper.map(promoCode, PromoCodeDto.class));
        assertNotNull(promoCodeDto1);
        assertEquals("adf", promoCodeDto1.getPromoCode());
    }

    @Test
    public void getPromoCode() {
        String promoCodeId = "uhuoinl";
        Mockito.when(promoCodeRepository.findByPromoCodeId(promoCodeId)).thenReturn(promoCode);
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCode(promoCodeId);
        assertNotNull(promoCodeDto);
    }

    @Test
    public void deletePromoCode() {
        String promoCodeId = "ytfvguj";
        Mockito.when(promoCodeRepository.findById(promoCodeId)).thenReturn(Optional.ofNullable(promoCode));//Mockito's when() is used to mock methods that return a value.
        promoCodeService.deletePromoCode(promoCodeId);
        verify(promoCodeRepository, times(1)).delete(promoCode);
    }

    @Test
    public void getAllPromoCode() {
        PromoCode promoCode1 = PromoCode.builder().promoCode("KJHGF").discount(87).build();
        List<PromoCode> promoCodeLists = Arrays.asList(promoCode1, promoCode1);
        Mockito.when(promoCodeRepository.findAll()).thenReturn(promoCodeLists);
        List<PromoCodeDto> promoCodeDtos = promoCodeService.getAllPromoCodes();
        assertNotNull(promoCodeDtos);
    }
}
