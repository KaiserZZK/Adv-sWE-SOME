package com.advswesome.advswesome.controller;


import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Mono<Prescription> getPrescriptionById(@PathVariable String prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId);
    }

    @PutMapping("/{prescriptionId}")
    public Mono<Prescription> updatePrescription(@PathVariable String prescriptionId, @RequestBody Prescription prescription) {

        // Check if the prescription with the given ID exists
        Mono<Prescription> existingPrescription = prescriptionService.getPrescriptionById(prescriptionId);

        return existingPrescription.flatMap(existing -> {
            // Assuming prescriptionId in the path is used to ensure you update the correct prescription
            prescription.setPrescriptionId(prescriptionId);
            return prescriptionService.updatePrescription(prescription);
        });

    }

    @DeleteMapping("/{prescriptionId}")
    public Mono<Void> deletePrescription(@PathVariable String prescriptionId) {
        return prescriptionService.deletePrescription(prescriptionId);
    }


    @GetMapping("/profile/{profileId}")
    public Flux<Prescription> getPrescriptionsByProfileId(@PathVariable String profileId) {
        return prescriptionService.getPrescriptionsByProfileId(profileId);
    }
}
