package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.PrescriptionRepository;
import com.advswesome.advswesome.repository.document.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository proscriptionRepository) {
        this.prescriptionRepository = proscriptionRepository;
    }

    public Mono<Prescription> createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Mono<Prescription> getPrescriptionById(String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }

    public Mono<Prescription> updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Mono<Void> deletePrescription(String prescriptionId) {
        return prescriptionRepository.deleteById(prescriptionId);
    }

    // return a list of rx under the given profileId
    public Flux<Prescription> getPrescriptionsByProfileId(String profileId) {
        return prescriptionRepository.findByProfileId(profileId);
    }

}
