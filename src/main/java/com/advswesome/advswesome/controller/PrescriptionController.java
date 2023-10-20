package com.advswesome.advswesome.controller;


import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Mono<Prescription> createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/{prescriptionId}")
    public Mono<ResponseEntity<Prescription>> getPrescriptionById(@PathVariable String prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{prescriptionId}")
    public Mono<ResponseEntity<Prescription>> updatePrescription(@PathVariable String prescriptionId, @RequestBody Prescription prescription) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .flatMap(existing -> {
                    prescription.setPrescriptionId(prescriptionId);
                    return prescriptionService.updatePrescription(prescription);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{prescriptionId}")
    public Mono<ResponseEntity<Void>> deletePrescription(@PathVariable String prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId)
                .flatMap(existing -> prescriptionService.deletePrescription(prescriptionId).thenReturn(existing))
                .map(prescription -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/profile/{profileId}")
    public Flux<Prescription> getPrescriptionsByProfileId(@PathVariable String profileId) {
        return prescriptionService.getPrescriptionsByProfileId(profileId);
    }
}
