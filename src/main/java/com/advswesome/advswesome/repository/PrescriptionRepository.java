package com.advswesome.advswesome.repository;

import com.advswesome.advswesome.repository.document.Prescription;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends FirestoreReactiveRepository<Prescription> {
}