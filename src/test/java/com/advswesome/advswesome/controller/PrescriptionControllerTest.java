package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Prescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.advswesome.advswesome.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.Mockito.*;

// controller test that mock the behavior of api call
public class PrescriptionControllerTest {

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(prescriptionController).build();
    }


    @Test
    @WithMockUser
    void testCreatePrescriptionSuccess() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");

        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());
        when(prescriptionService.createPrescription(any(Prescription.class))).thenReturn(Mono.just(mockPrescription));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String mockPrescriptionJson = objectMapper.writeValueAsString(mockPrescription);

            mockMvc.perform(post("/prescriptions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockPrescriptionJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.prescriptionId").value("testId"));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testGetPrescriptionByIdExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));

        try {
            mockMvc.perform(get("/prescriptions/testId"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.prescriptionId").value("testId"));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testGetPrescriptionByIdDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(get("/prescriptions/testId"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testUpdatePrescriptionExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));
        when(prescriptionService.updatePrescription(any(Prescription.class))).thenReturn(Mono.just(mockPrescription));

        ObjectMapper objectMapper = new ObjectMapper();


        try {
            String mockPrescriptionJson = objectMapper.writeValueAsString(mockPrescription);
            mockMvc.perform(put("/prescriptions/testId")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mockPrescriptionJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.prescriptionId").value("testId"));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testUpdatePrescriptionDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String emptyPrescriptionJson = objectMapper.writeValueAsString(new Prescription());
            mockMvc.perform(put("/prescriptions/testId")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(emptyPrescriptionJson))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testDeletePrescriptionExists() {
        Prescription mockPrescription = new Prescription();
        mockPrescription.setPrescriptionId("testId");
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.just(mockPrescription));
        when(prescriptionService.deletePrescription("testId")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(delete("/prescriptions/testId"))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testDeletePrescriptionDoesNotExist() {
        when(prescriptionService.getPrescriptionById("testId")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(delete("/prescriptions/testId"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @WithMockUser
    void testGetPrescriptionsByProfileId() {
        String profileId = "profile123";
        Prescription mockPrescription1 = new Prescription();
        mockPrescription1.setPrescriptionId("prescriptionId1");
        Prescription mockPrescription2 = new Prescription();
        mockPrescription2.setPrescriptionId("prescriptionId2");
        List<Prescription> mockPrescriptions = Arrays.asList(
                mockPrescription1,
                mockPrescription2);

        when(prescriptionService.getPrescriptionsByProfileId(profileId)).thenReturn(Flux.fromIterable(mockPrescriptions));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            mockMvc.perform(get("/prescriptions/profile/" + profileId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].prescriptionId").value("prescriptionId1"))
                    .andExpect(jsonPath("$[1].prescriptionId").value("prescriptionId2"));
            // Add more assertions for other fields if necessary
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }
}
