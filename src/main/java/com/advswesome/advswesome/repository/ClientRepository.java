package com.advswesome.advswesome.repository;

import com.advswesome.advswesome.repository.document.Client;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends FirestoreReactiveRepository<Client> {
}
