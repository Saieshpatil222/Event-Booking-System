package com.promocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promocode.dto.PromoCodeDto;
import com.promocode.service.PromoCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromoCodeController.class) // Use this to test only the controller
@AutoConfigureMockMvc(addFilters = false) // Disable security filters if required
public class PromoCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromoCodeService promoCodeService; // Mock the service dependency

    private PromoCodeDto requestDto;

    private PromoCodeDto responseDto;

    @BeforeEach
    void setUp() {
        // Setup request DTO
        requestDto = PromoCodeDto.builder()
                .promoCode("SUMMER2025")
                .discount(20)
                .build();

        // Setup response DTO (includes generated ID)
        responseDto = PromoCodeDto.builder().promoCodeId(UUID.randomUUID().toString())
                .promoCode("SUMMER2025")
                .discount(20)
                .build();
    }

    @Test
    void createPromoCode_ShouldReturnCreatedPromoCode() throws Exception {
        // Mock service method
        when(promoCodeService.createPromoCode(any(PromoCodeDto.class)))
                .thenReturn(responseDto);

        // Perform POST request
        mockMvc.perform(post("/promocode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.promocodeId").exists())
                .andExpect(jsonPath("$.promoCode").value("SUMMER2025"))
                .andExpect(jsonPath("$.discount").value(20))
                .andDo(print());

        // Verify service was called
        verify(promoCodeService, times(1))
                .createPromoCode(any(PromoCodeDto.class));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
