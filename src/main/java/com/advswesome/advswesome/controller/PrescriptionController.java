package com.advswesome.advswesome.controller;


import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Prescription> createProfile(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/{prescriptionId}")
    public Mono<Prescription> getPrescriptionById(@PathVariable String prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId);
    }

    @PutMapping("/{prescriptionId}")
    public Mono<Prescription> updateProfile(@PathVariable String prescriptionId, @RequestBody Prescription prescription) {

        // Check if the profile with the given ID exists
        Mono<Prescription> existingProfile = prescriptionService.getPrescriptionById(prescriptionId);

        return existingProfile.flatMap(existing -> {
            // Assuming profileId in the path is used to ensure you update the correct profile
            prescription.setPrescriptionId(prescriptionId);
            return prescriptionService.updatePrescription(prescription);
        });

    }

    @DeleteMapping("/{prescriptionId}")
    public Mono<Void> deletePrescription(@PathVariable String prescriptionId) {
        return prescriptionService.deletePrescription(prescriptionId);
    }
}
