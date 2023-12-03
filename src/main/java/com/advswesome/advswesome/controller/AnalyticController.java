package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.ClientService;
import com.advswesome.advswesome.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/analytics/")
public class AnalyticController {


//    private final AnalyticService analyticService;
//
//    @Autowired
//    public AnalyticController(AnalyticService analyticService) {
//        this.analyticService = analyticService;
//    }
    private final AnalyticService analyticService;


    @Autowired
    public AnalyticController(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }


    @GetMapping("/{profileId}")
    public ResponseEntity<String> getHealthAdvice(@RequestBody String profileId) {
//        return analyticService.getHealthAdvice(profileId)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());

        return ResponseEntity.ok(analyticService.getHealthAdvice(profileId));
    }


}
