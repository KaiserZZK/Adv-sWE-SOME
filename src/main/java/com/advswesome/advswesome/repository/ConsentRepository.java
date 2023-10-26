package com.advswesome.advswesome.repository;

import com.advswesome.advswesome.repository.document.Consent;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ConsentRepository extends FirestoreReactiveRepository<Consent> {
    Mono<Consent> findByProfileId(String profileId);
    Mono<Consent> findByUserId(String userId);
}