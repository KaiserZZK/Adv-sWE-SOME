package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/consents")
public class ConsentController {

    private final ConsentService consentService;

    @Autowired
    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping
    public Mono<Consent> createConsent(@RequestBody Consent consent) {
        return consentService.createConsent(consent);
    }

    @GetMapping("/{consentId}")
    public Mono<Consent> getConsentById(@PathVariable String consentId) {
        return consentService.getConsentById(consentId);
    }

    @PutMapping("/{consentId}")
    public Mono<Consent> updateConsent(@PathVariable String consentId, @RequestBody Consent consent) {

        // Check if the consent with the given ID exists
        Mono<Consent> existingConsent = consentService.getConsentById(consentId);

        return existingConsent.flatMap(existing -> {
            // Assuming consentId in the path is used to ensure you update the correct consent
            consent.setConsentId(consentId);
            return consentService.updateConsent(consent);
        });
    }

    @DeleteMapping("/{consentId}")
    public Mono<Void> deleteConsent(@PathVariable String consentId) {
        return consentService.deleteConsent(consentId);
    }
}
