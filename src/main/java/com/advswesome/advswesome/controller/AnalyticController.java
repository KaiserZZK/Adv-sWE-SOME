package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.PrescriptionService;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<String> getHealthAdvice(@RequestBody String profileId) {

        // TODO: get prescription info

        // TODO: generate prompt

        String prompt = "I am a 46 yo male, 140 pounds, currently taking omeprazole. Give me three health advices. Make the response in a json list where each item is an advice like {\n" +
                "  \"advice_1\": \"xxxxx.\",\n" +
                "  \"advice_2\": \"xxxx.\",\n" +
                "  \"advice_3\": \"xxx.\"\n" +
                "}";

        return ResponseEntity.ok(analyticService.getHealthAdvice(prompt));
    }


}
