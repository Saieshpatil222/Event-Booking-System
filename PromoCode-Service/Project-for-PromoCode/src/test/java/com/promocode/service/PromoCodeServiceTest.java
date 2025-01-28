package com.promocode.service;

import com.promocode.dto.PromoCodeDto;
import com.promocode.entity.PromoCode;
import com.promocode.repository.PromoCodeRepository;
import com.promocode.service.impl.PromoCodeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PromoCodeServiceTest {

    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Test
    @WithMockUser("ROLE_ADMIN")
    public void createPromoCodeTest() {

        PromoCodeDto promoCodeDto = PromoCodeDto.builder().promoCode("fgh").promoCodeId("tghu").discount(90).build();
        PromoCode code = PromoCode.builder().promoCode("fgh").promoCodeId("tghu").discount(90).build();
        PromoCode savedCode = PromoCode.builder().promoCode("fgh").promoCodeId("tghu").discount(90).build();
        Mockito.when(modelMapper.map(any(PromoCodeDto.class), any(Class.class))).thenReturn(savedCode);
        Mockito.when(promoCodeRepository.save(Mockito.any())).thenReturn(savedCode);
        Mockito.when(modelMapper.map(any(PromoCode.class), any(Class.class))).thenReturn(promoCodeDto);
        PromoCodeDto promoCodeDto1 = promoCodeService.createPromoCode(promoCodeDto);
        assertNotNull(promoCodeDto1);
    }

    @Test
    public void getPromoCode() {
        PromoCodeDto promoCodeDto = PromoCodeDto.builder().promoCode("fgh").discount(90).build();
        PromoCode promoCode = PromoCode.builder().promoCode("fgh").discount(90).build();
        String promoCodeId = "uhuoinl";
        Mockito.when(promoCodeRepository.findById(anyString())).thenReturn(Optional.of(promoCode));
        Mockito.when(modelMapper.map(any(PromoCode.class), any(Class.class))).thenReturn(promoCodeDto);
        PromoCodeDto singlePromoCode = this.promoCodeService.getPromoCode(promoCodeId);
        Assertions.assertNotNull(singlePromoCode);
    }

    @Test
    public void deletePromoCode() {
        PromoCodeDto promoCodeDto = PromoCodeDto.builder().promoCode("fgh").discount(90).build();
        PromoCode promoCode = PromoCode.builder().promoCode("fgh").discount(90).build();
        String promoCodeId = "ytfvguj";
        Mockito.when(promoCodeRepository.findById(anyString())).thenReturn(Optional.of(promoCode));//Mockito's when() is used to mock methods that return a value.
        this.promoCodeService.deletePromoCode(promoCodeId);
        verify(promoCodeRepository, times(1)).delete(promoCode);
    }

    @Test
    public void getAllPromoCode() {
        PromoCode promoCode1 = PromoCode.builder().promoCode("KJHGF").discount(87).build();
        PromoCode promoCode2 = PromoCode.builder().promoCode("KJHGF").discount(87).build();
        PromoCode promoCode3 = PromoCode.builder().promoCode("KJHGF").discount(87).build();

        PromoCodeDto promoCodeDto1 = PromoCodeDto.builder().promoCode("KJHGF").discount(87).build();
        PromoCodeDto promoCodeDto2 = PromoCodeDto.builder().promoCode("KJHGF").discount(87).build();
        PromoCodeDto promoCodeDto3 = PromoCodeDto.builder().promoCode("KJHGF").discount(87).build();

        List<PromoCode> promoCodeLists = Arrays.asList(promoCode1, promoCode2, promoCode3);
        List<PromoCodeDto> promoCodeDtoList = Arrays.asList(promoCodeDto1, promoCodeDto2, promoCodeDto3);

        Mockito.when(promoCodeRepository.findAll()).thenReturn(promoCodeLists);
        Mockito.when(modelMapper.map(promoCode1, PromoCodeDto.class)).thenReturn(promoCodeDto1);
        Mockito.when(modelMapper.map(promoCode2, PromoCodeDto.class)).thenReturn(promoCodeDto2);
        Mockito.when(modelMapper.map(promoCode3, PromoCodeDto.class)).thenReturn(promoCodeDto3);

        List<PromoCodeDto> promoCodeDtos = promoCodeService.getAllPromoCodes();
        assertNotNull(promoCodeDtos);
    }
}
