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
    void testCreateConsent() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when createConsent called, return the same consent
        when(consentService.createConsent(consent)).thenReturn(Mono.just(consent));

        // createConsent from the controller
        consentController.createConsent(consent).block();

        // verify the createConsent method from consentService only called once
        verify(consentService, times(1)).createConsent(consent);
    }


    @Test
    void testGetByConsentId(){
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when getConsentById called, return the same consent
        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));

        // getConsentById from the controller
        consentController.getConsentById("123").block();

        // verify the getConsentById method from consentService only called once
        verify(consentService, times(1)).getConsentById("123");
    }


    @Test
    void testUpdateConsent(){
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when getConsentById & updateConsent called, return the same consent
        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.updateConsent(consent)).thenReturn(Mono.just(consent));

        // updateConsent from the controller
        consentController.updateConsent("123", consent).block();

        // verify the updateConsent method from consentService only called once
        verify(consentService, times(1)).updateConsent(consent);
    }

    @Test
    void testDeleteConsent() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when getConsentById & deleteConsent called, return the same consent
        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.deleteConsent("123")).thenReturn(Mono.empty());

        // deleteConsent from the controller
        consentController.deleteConsent("123").block();

        // verify the deleteConsent method from consentService only called once
        verify(consentService, times(1)).deleteConsent("123");
    }

}
