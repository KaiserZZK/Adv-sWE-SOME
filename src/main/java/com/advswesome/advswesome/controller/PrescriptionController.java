package com.advswesome.advswesome.controller;


import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.security.UserPrincipal;
import com.advswesome.advswesome.service.PrescriptionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody Prescription prescription
    ) {
        Mono<Prescription> prescriptionMono = prescriptionService.createPrescription(prescription);
        Prescription newPrescription  = prescriptionMono.block();
        return ResponseEntity.status(HttpStatus.CREATED).body(newPrescription);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> getPrescriptionById(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String prescriptionId
    ) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> updatePrescription(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String prescriptionId,
        @RequestBody Prescription prescription
    ) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .flatMap(existing -> {
                    prescription.setPrescriptionId(prescriptionId);
                    return prescriptionService.updatePrescription(prescription);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deletePrescription(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String prescriptionId
    ) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .flatMap(existing -> prescriptionService.deletePrescription(prescriptionId).thenReturn(existing))
                .map(prescription -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }


    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByProfileId(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId
    ) {
        Flux<Prescription> prescriptionsFlux = prescriptionService.getPrescriptionsByProfileId(profileId);
        List<Prescription> prescriptionsList = prescriptionsFlux.collectList().block();
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionsList);
    }

}
