package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<String>> createConsent(@RequestBody Consent consent) {
        return consentService.getConsentById(consent.getConsentId())
                .flatMap(existingConsent ->
                        Mono.just(new ResponseEntity<String>("Consent with ID " + consent.getConsentId() + " already exists.", HttpStatus.CONFLICT)))
                .switchIfEmpty (
                        consentService.createConsent(consent)
                                .then(Mono.just(new ResponseEntity<String>("Consent created successfully with ID " + consent.getConsentId(), HttpStatus.CREATED)))
                );
    }

    @GetMapping("/{consentId}")
    public Mono<ResponseEntity<Consent>> getConsentById(@PathVariable String consentId) {
        return consentService.getConsentById(consentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{consentId}")
    public Mono<ResponseEntity<Consent>> updateConsent(@PathVariable String consentId, @RequestBody Consent consent) {

        // Check if the consent with the given ID exists
        Mono<Consent> existingConsent = consentService.getConsentById(consentId);

        return existingConsent.flatMap(existing -> {
            // Assuming consentId in the path is used to ensure you update the correct consent
            // Every time we update the consent, we need to update the time here
            Date date = new Date();
            consent.setUpdatedAt(date);
            consent.setConsentId(consentId);
            return consentService.updateConsent(consent);
        })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{consentId}")
    public Mono<ResponseEntity<Void>> deleteConsent(@PathVariable String consentId) {
        return consentService.getConsentById(consentId)
                .flatMap(existing -> consentService.deleteConsent(consentId).thenReturn(existing))
                .map(prescription -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile/{profileId}")
    public Mono<Consent> getConsentByProfileId(@PathVariable String profileId) {
        return consentService.getConsentByProfileId(profileId);
    }
}
