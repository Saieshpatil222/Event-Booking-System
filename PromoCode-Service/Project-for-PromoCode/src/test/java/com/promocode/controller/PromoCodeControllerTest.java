package com.promocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promocode.dto.PromoCodeDto;
import com.promocode.service.PromoCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PromoCodeControllerTest {

    @Mock
    private PromoCodeService promoCodeService;

    @InjectMocks
    private PromoCodeController promoCodeController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(promoCodeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createPromoCodeTest() throws Exception {
        PromoCodeDto promoCodeDto = PromoCodeDto.builder().promoCodeId("123").promoCode("ABC").discount(88).build();
        PromoCodeDto responseDto = PromoCodeDto.builder().promoCodeId("123").promoCode("ABC").discount(88).build();

        Mockito.when(promoCodeService.createPromoCode(any(PromoCodeDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/promocode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(promoCodeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.promoCodeId").value(123))
                .andDo(print());

        Mockito.verify(promoCodeService, Mockito.times(1)).createPromoCode(any(PromoCodeDto.class));
    }

    @Test
    public void getAllPromoCodesTest() throws Exception {
        PromoCodeDto promoCodeDto1 = PromoCodeDto.builder().promoCodeId("123").promoCode("ABC").discount(88).build();
        PromoCodeDto promoCodeDto2 = PromoCodeDto.builder().promoCodeId("124").promoCode("XYZ").discount(89).build();
        PromoCodeDto promoCodeDto3 = PromoCodeDto.builder().promoCodeId("125").promoCode("MNC").discount(90).build();

        List<PromoCodeDto> promoCodeDtoList = Arrays.asList(promoCodeDto1, promoCodeDto2, promoCodeDto3);

        Mockito.when(promoCodeService.getAllPromoCodes()).thenReturn(promoCodeDtoList);

        mockMvc.perform(get("/promocode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(promoCodeDtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].promoCodeId").value("123"))
                .andExpect(jsonPath("$[0].promoCode").value("ABC"))
                .andExpect(jsonPath("$[0].discount").value("88"))
                .andExpect(jsonPath("$[1].promoCodeId").value("124"))
                .andExpect(jsonPath("$[1].promoCode").value("XYZ"))
                .andExpect(jsonPath("$[1].discount").value("89"))
                .andDo(print());

        Mockito.verify(promoCodeService, Mockito.times(1)).getAllPromoCodes();
    }

    @Test
    public void deletePromoCodeTest() throws Exception {
        String promoCodeId = "promocode123";
        Mockito.doNothing().when(promoCodeService).deletePromoCode(promoCodeId);

        mockMvc.perform(delete("/promocode/{promocode}", promoCodeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("PromoCode Deleted Successfully"));

        Mockito.verify(promoCodeService, Mockito.times(1)).deletePromoCode(anyString());
    }
}
