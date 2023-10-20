package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrescriptionControllerTest {

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(prescriptionController).build();
    }

    @Test
    void testGetPrescriptionByIdExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));

        webTestClient.get()
                .uri("/prescriptions/testId")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Prescription.class)
                .consumeWith(response -> {
                    assertEquals(mockPrescription.getPrescriptionId(), Objects.requireNonNull(response.getResponseBody()).getPrescriptionId());
                });
    }

    @Test
    void testGetPrescriptionByIdDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/prescriptions/testId")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdatePrescriptionExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));
        when(prescriptionService.updatePrescription(any(Prescription.class))).thenReturn(Mono.just(mockPrescription));

        webTestClient.put()
                .uri("/prescriptions/testId")
                .bodyValue(mockPrescription)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Prescription.class)
                .consumeWith(response -> {
                    assertEquals(mockPrescription.getPrescriptionId(), Objects.requireNonNull(response.getResponseBody()).getPrescriptionId());
                });
    }

    @Test
    void testUpdatePrescriptionDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/prescriptions/testId")
                .bodyValue(new Prescription())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeletePrescriptionExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));
        when(prescriptionService.deletePrescription("testId")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/prescriptions/testId")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeletePrescriptionDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/prescriptions/testId")
                .exchange()
                .expectStatus().isNotFound();
    }
}