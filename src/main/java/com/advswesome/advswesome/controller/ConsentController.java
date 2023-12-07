package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.security.UserPrincipal;
import com.advswesome.advswesome.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Date;

@RestController
@RequestMapping("/consents")
public class ConsentController {

    private final ConsentService consentService;

    @Autowired
    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping
    public ResponseEntity<String> createConsent(
        @AuthenticationPrincipal UserPrincipal principal, 
        @RequestBody Consent consent) 
    {
        return consentService.getConsentById(consent.getConsentId())
                .flatMap(existingConsent ->
                        Mono.just(new ResponseEntity<String>("Consent with ID " + consent.getConsentId() + " already exists.", HttpStatus.CONFLICT)))
                .switchIfEmpty (
                        consentService.createConsent(consent)
                                .then(Mono.just(new ResponseEntity<String>("Consent created successfully with ID " + consent.getConsentId(), HttpStatus.CREATED)))
                )
                .block();
    }

    @GetMapping("/{consentId}")
    public ResponseEntity<Consent> getConsentById(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String consentId) 
    {
        return consentService.getConsentById(consentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @PutMapping("/{consentId}")
    public ResponseEntity<Consent> updateConsent(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String consentId, 
        @RequestBody Consent consent) 
    {
        Mono<Consent> existingConsent = consentService.getConsentById(consentId);
        
        return existingConsent.flatMap(existing -> {
            // Assuming consentId in the path is used to ensure you update the correct consent
            // If no specific time given, the time updated is when we update the consent
            if (consent.getUpdatedAt() == null){
                Date date = new Date();
                consent.setUpdatedAt(date);
            }
            consent.setConsentId(consentId);
            return consentService.updateConsent(consent);
        })
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build())
        .block();
    }

    @DeleteMapping("/{consentId}")
    public ResponseEntity<Void> deleteConsent(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String consentId
    ) {
        return consentService.getConsentById(consentId)
                .flatMap(existing -> consentService.deleteConsent(consentId).thenReturn(existing))
                .map(prescription -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<Consent> getConsentByProfileId(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId) 
    {
        Mono<Consent> consentMono = consentService.getConsentByProfileId(profileId);
        Consent foundConsent  = consentMono.block();
        return ResponseEntity.status(HttpStatus.OK).body(foundConsent);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Consent> getConsentByUserId(@PathVariable String userId) {
        Mono<Consent> consentMono = consentService.getConsentByUserId(userId);
        Consent foundConsent  = consentMono.block();
        return ResponseEntity.status(HttpStatus.OK).body(foundConsent);
    }
}
