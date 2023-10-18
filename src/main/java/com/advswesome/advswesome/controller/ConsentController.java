package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
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
            // Every time we update the consent, we need to update the time here
            Date date = new Date();
            consent.setUpdatedAt(date);
            consent.setConsentId(consentId);
            return consentService.updateConsent(consent);
        });
    }

    @DeleteMapping("/{consentId}")
    public Mono<Void> deleteConsent(@PathVariable String consentId) {
        return consentService.deleteConsent(consentId);
    }

    @GetMapping("/profile/{profileId}")
    public Mono<Consent> getConsentByProfileId(@PathVariable String profileId) {
        return consentService.getConsentByProfileId(profileId);
    }
}
