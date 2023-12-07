package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.security.JwtIssuer;
import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.PrescriptionService;
import com.advswesome.advswesome.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AnalyticControllerTest {
    @MockBean
    private AnalyticService analyticService;

    @MockBean
    private PrescriptionService prescriptionService;

    @MockBean
    private ProfileService profileService;

    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtIssuer jwtIssuer;

    private String token;

    @BeforeEach
    void setUp() {
        try {
            token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("12")
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());
        } catch (Exception e) {
            fail();
        }
        this.webTestClient = WebTestClient
                .bindToController(new AnalyticController(analyticService, prescriptionService, profileService))
                .configureClient()
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    @Test
    void getHealthAdvice_Success() {
        String profileId = "123";

        Profile.MedicalHistory medicalHistory = new Profile.MedicalHistory("dn", "1","1");

        Profile mockProfile = new Profile();
        mockProfile.setUserId("12");
        mockProfile.setProfileId(profileId);
        mockProfile.setMedicalHistory(Arrays.asList(medicalHistory));

        Prescription mockPrescription = new Prescription();
        mockPrescription.setRxName("rxname");

        when(profileService.getProfileById(profileId)).thenReturn(Mono.just(mockProfile));
        when(prescriptionService.getPrescriptionsByProfileId(profileId)).thenReturn(Flux.just(mockPrescription));
        when(analyticService.getHealthAdvice(any(String.class))).thenReturn("mocked advice");

        webTestClient.get()
                .uri("/analytics/" + profileId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("mocked advice");
    }

}
