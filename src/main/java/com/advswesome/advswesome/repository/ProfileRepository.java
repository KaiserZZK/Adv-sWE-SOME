package com.advswesome.advswesome.repository;

import com.advswesome.advswesome.repository.document.Profile;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProfileRepository extends FirestoreReactiveRepository<Profile> {
    Flux<Profile> findByUserId(String userId);
}
