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

        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentRepository.save(consent)).thenReturn(Mono.just(consent));
        Consent savedConsent = consentService.createConsent(consent).block();

        assertNotNull(savedConsent);
        assertEquals(consent.getConsentId(), savedConsent.getConsentId());
        assertEquals(consent.getProfileId(), savedConsent.getProfileId());
        assertEquals(consent.isPermission(), savedConsent.isPermission());
        assertEquals(consent.getUpdatedAt(), savedConsent.getUpdatedAt());

        verify(consentRepository, times(1)).save(consent);
    }

    @Test
    void testGetConsentByID(){
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentRepository.findById("123")).thenReturn(Mono.just(consent));

        Consent fetchedConsent = consentService.getConsentById("123").block();
        assertNotNull(fetchedConsent);
        assertEquals(consent.getConsentId(), fetchedConsent.getConsentId());
        assertEquals(consent.getProfileId(), fetchedConsent.getProfileId());
        assertEquals(consent.isPermission(), fetchedConsent.isPermission());
        assertEquals(consent.getUpdatedAt(), fetchedConsent.getUpdatedAt());
    }


    @Test
    void testUpdate(){
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentRepository.findById("123")).thenReturn(Mono.just(consent));
        when(consentRepository.save(any(Consent.class))).thenReturn(Mono.just(consent));

        consent.setPermission(false);
        Consent fetchedConsent = consentService.updateConsent(consent).block();
        assertNotNull(fetchedConsent);
        assertFalse(fetchedConsent.isPermission());
    }

    @Test
    void testDeleteRx() {
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        when(consentRepository.findById("123")).thenReturn(Mono.just(consent));
        when(consentRepository.deleteById("123")).thenReturn(Mono.empty());

        consentService.deleteConsent("123").block();

        verify(consentRepository, times(1)).deleteById("123");
    }

}



