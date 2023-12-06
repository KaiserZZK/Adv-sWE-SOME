package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

public class ConsentControllerTest {

    @InjectMocks
    private ConsentController consentController;

    @Mock
    private ConsentService consentService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consentController).build();
    }

    @Test
    @WithMockUser
    void testCreateConsentSuccess() throws Exception {
        Date date = new Date();
        Consent mockConsent = new Consent("123", "User1", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());
        when(consentService.createConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        mockMvc.perform(post("/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockConsent)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Consent created successfully with ID 123"));
    }

    @Test
    @WithMockUser
    void testCreateConflict() throws Exception {
        Date date = new Date();
        Consent mockConsent = new Consent("123", "User1", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));
        when(consentService.createConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        try {
            mockMvc.perform(post("/consents")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockConsent)))
                    .andExpect(status().isConflict())
                    .andExpect(content().string("Consent with ID 123 already exists."));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void testGetByConsentIdExists() throws Exception {
        Date date = new Date();
        Consent mockConsent = new Consent("123", "User1", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));

        mockMvc.perform(get("/consents/123"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockConsent)));
    }

    @Test
    void testGetConsentByIdDoesNotExist() throws Exception {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        mockMvc.perform(get("/consents/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateConsentExists() throws Exception {
        Date date = new Date();
        Consent mockConsent = new Consent("123", "User1", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));
        when(consentService.updateConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        mockMvc.perform(put("/consents/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockConsent)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockConsent)));
    }

    @Test
    void testUpdateConsentDoesNotExist() throws Exception {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        mockMvc.perform(put("/consents/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Consent())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteConsentExists() throws Exception {
        Date date = new Date();
        Consent consent = new Consent("123", "User1", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.deleteConsent("123")).thenReturn(Mono.empty());

        mockMvc.perform(delete("/consents/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteConsentDoesNotExist() throws Exception {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        mockMvc.perform(delete("/consents/123"))
                .andExpect(status().isNotFound());
    }

}
