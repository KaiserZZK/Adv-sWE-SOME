package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Date;


import static org.mockito.Mockito.*;

public class ConsentControllerTest {


    @InjectMocks
    private ConsentController consentController;

    @Mock
    private ConsentService consentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConsent() throws Exception {

        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentService.createConsent(consent)).thenReturn(Mono.just(consent));
        consentController.createConsent(consent).block();

        verify(consentService, times(1)).createConsent(consent);

    }


    @Test
    void testGetbyConsentId(){

        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        consentController.getConsentById("123").block();
        verify(consentService, times(1)).getConsentById("123");

    }


    @Test
    void testUpdateConsent(){
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.updateConsent(consent)).thenReturn(Mono.just(consent));

        consentController.updateConsent("123", consent).block();
        verify(consentService, times(1)).getConsentById("123");

    }

    @Test
    void testDeleteConsent() {
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.deleteConsent("123")).thenReturn(Mono.empty());

        consentController.deleteConsent("123").block();

        verify(consentService, times(1)).deleteConsent("123");
    }
}
