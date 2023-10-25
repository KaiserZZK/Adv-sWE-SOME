package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConsentControllerTest {


    @InjectMocks
    private ConsentController consentController;

    @Mock
    private ConsentService consentService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(consentController).build();
    }

    @Test
    void testCreateConsentSuccess() {
        // create a new consent
        Date date = new Date();
        Consent mockConsent = new Consent("123", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());
        when(consentService.createConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        webTestClient.post()
                .uri("/consents")
                .bodyValue(mockConsent)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Consent created successfully with ID 123");
    }

    @Test
    void testCreateConflict() {
        // create a new consent
        Date date = new Date();
        Consent mockConsent = new Consent("123", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));
        when(consentService.createConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        webTestClient.post()
                .uri("/consents")
                .bodyValue(mockConsent)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class)
                .isEqualTo("Consent with ID 123 already exists.");
    }

    @Test
    void testGetByConsentIdExists(){
        // create a new consent
        Date date = new Date();
        Consent mockConsent = new Consent("123", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));

        webTestClient.get()
                .uri("/consents/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Consent.class)
                .consumeWith(response -> {
                    assertEquals(mockConsent.getConsentId(), Objects.requireNonNull(response.getResponseBody()).getConsentId());
                });
    }

    @Test
    void testGetConsentByIdDoesNotExist() {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/consents/123")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateConsentExists(){
        // create a new consent
        Date date = new Date();
        Consent mockConsent = new Consent("123", "A2", true, date);
        when(consentService.getConsentById("123")).thenReturn(Mono.just(mockConsent));
        when(consentService.updateConsent(any(Consent.class))).thenReturn(Mono.just(mockConsent));

        webTestClient.put()
                .uri("/consents/123")
                .bodyValue(mockConsent)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Consent.class)
                .consumeWith(response -> {
                    assertEquals(mockConsent.getConsentId(), Objects.requireNonNull(response.getResponseBody()).getConsentId());
                });
    }

    @Test
    void testUpdateConsentDoesNotExist() {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/consents/123")
                .bodyValue(new Consent())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteConsentExists() {
        // create a new consent
        Date date = new Date();
        Consent consent = new Consent("123", "A2", true, date);

        // when getConsentById & deleteConsent called, return the same consent
        when(consentService.getConsentById("123")).thenReturn(Mono.just(consent));
        when(consentService.deleteConsent("123")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/consents/123")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteConsentDoesNotExist() {
        when(consentService.getConsentById("123")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/consents/123")
                .exchange()
                .expectStatus().isNotFound();
    }

}
