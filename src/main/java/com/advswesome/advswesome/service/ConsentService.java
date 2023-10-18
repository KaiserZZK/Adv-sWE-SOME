package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ConsentRepository;
import com.advswesome.advswesome.repository.document.Consent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConsentService {
    private final ConsentRepository consentRepository;

    @Autowired
    public ConsentService(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    public Mono<Consent> createConsent(Consent consent) {
        return consentRepository.save(consent);
    }

    public Mono<Consent> getConsentById(String consentId) {
        return consentRepository.findById(consentId);
    }

    public Mono<Consent> getConsentByProfileId(String profileId) {
        return consentRepository.findByProfileId(profileId);
    }

    public Mono<Consent> updateConsent(Consent consent) {
        return consentRepository.save(consent);
    }

    public Mono<Void> deleteConsent(String consentId) {
        return consentRepository.deleteById(consentId);
    }
}
