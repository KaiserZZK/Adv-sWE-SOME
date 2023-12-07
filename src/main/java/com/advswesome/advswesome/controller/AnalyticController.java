package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.security.UserPrincipal;
import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.PrescriptionService;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/analytics/")
public class AnalyticController {



    private final AnalyticService analyticService;
    private final PrescriptionService prescriptionService;
    private final ProfileService profileService;


    @Autowired
    public AnalyticController(AnalyticService analyticService, PrescriptionService prescriptionService, ProfileService profileService) {
        this.analyticService = analyticService;
        this.prescriptionService = prescriptionService;
        this.profileService = profileService;
    }


    @GetMapping("/{profileId}")
    public ResponseEntity<String> getHealthAdvice(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String profileId) {

        Flux<Prescription> prescriptionsFlux = prescriptionService.getPrescriptionsByProfileId(profileId);
        List<Prescription> prescriptionsList = prescriptionsFlux.collectList().block();

        Mono<Profile> profileMono = profileService.getProfileById(profileId);
        Profile foundProfile  = profileMono.block();

        if (foundProfile == null) {
            return ResponseEntity.notFound().build();
        }


        String prompt = "I am a " + foundProfile.getAge() + " yo " + foundProfile.getSex() + ", living in " + foundProfile.getLocation() +
                ", with a physical fitness level of " + foundProfile.getPhysicalFitness() + " and language preference of " + foundProfile.getLanguagePreference() +
                ". My medical history includes: ";

        for (Profile.MedicalHistory entry : foundProfile.getMedicalHistory()) {
            prompt += entry.getDiseaseName() + " ";
        }

        prompt += "I am currently taking ";

        for (Prescription prescription : prescriptionsList) {
            prompt += prescription.getRxName() + " ";
        }

        prompt = prompt.substring(0, prompt.length() - 2); // Remove the last comma and space

        prompt += ". Give me three health advices. Make the response in a json list where each item is an advice like {" +
                "  advice_1: xxxx.," +
                "  advice_2: xxxx.," +
                "  advice_3: xxxx." +
                "}";


        return ResponseEntity.ok(analyticService.getHealthAdvice(prompt));
    }


}
