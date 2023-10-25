package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ConsentRepository;
import com.advswesome.advswesome.repository.document.Consent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsentServiceTest {
    @InjectMocks
    private ConsentService consentService;

    @Mock
    private ConsentRepository consentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConsent() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when save method is called, use Mockito to return a Mono of consent
        when(consentRepository.save(consent)).thenReturn(Mono.just(consent));
        Consent savedConsent = consentService.createConsent(consent).block();

        // check consent content
        assertNotNull(savedConsent);
        assertEquals(consent.getConsentId(), savedConsent.getConsentId());
        assertEquals(consent.getProfileId(), savedConsent.getProfileId());
        assertEquals(consent.isPermission(), savedConsent.isPermission());
        assertEquals(consent.getUpdatedAt(), savedConsent.getUpdatedAt());

        // verify the save method is called exactly once
        verify(consentRepository, times(1)).save(consent);
    }

    @Test
    void testGetConsentByID(){
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when findById method is called, use Mockito to return a Mono of consent
        when(consentRepository.findById("123")).thenReturn(Mono.just(consent));
        Consent fetchedConsent = consentService.getConsentById("123").block();

        // check consent content
        assertNotNull(fetchedConsent);
        assertEquals(consent.getConsentId(), fetchedConsent.getConsentId());
        assertEquals(consent.getProfileId(), fetchedConsent.getProfileId());
        assertEquals(consent.isPermission(), fetchedConsent.isPermission());
        assertEquals(consent.getUpdatedAt(), fetchedConsent.getUpdatedAt());

        // verify the findById method is called exactly once
        verify(consentRepository, times(1)).findById("123");
    }


    @Test
    void testUpdate(){
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // save and then update consent
        when(consentRepository.save(consent)).thenReturn(Mono.just(consent));
        consent.setPermission(false);

        // block the return value of the updated consent
        Consent fetchedConsent = consentService.updateConsent(consent).block();

        // check consent content
        assertNotNull(fetchedConsent);
        assertFalse(fetchedConsent.isPermission());
    }

    @Test
    void testDeleteConsent() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when deleteById method is called, use Mockito to return a Mono of consent
        when(consentRepository.deleteById("123")).thenReturn(Mono.empty());
        consentService.deleteConsent("123").block();

        // verify the deleteById method is called exactly once
        verify(consentRepository, times(1)).deleteById("123");
    }

    @Test
    void testGetConsentByProfileId() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when findByProfileId method is called, use Mockito to return a Mono of consent
        when(consentRepository.findByProfileId("A2")).thenReturn(Mono.just(consent));
        Consent fetchedConsent = consentService.getConsentByProfileId("A2").block();

        // check consent content
        assertNotNull(fetchedConsent);
        assertEquals(consent.getConsentId(), fetchedConsent.getConsentId());
        assertEquals(consent.getProfileId(), fetchedConsent.getProfileId());
        assertEquals(consent.isPermission(), fetchedConsent.isPermission());
        assertEquals(consent.getUpdatedAt(), fetchedConsent.getUpdatedAt());

        // verify the findByProfileId method is called exactly once
        verify(consentRepository, times(1)).findByProfileId("A2");
    }

}



