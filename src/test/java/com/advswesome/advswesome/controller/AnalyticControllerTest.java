package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.PrescriptionService;
import com.advswesome.advswesome.service.ProfileService;
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

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AnalyticControllerTest {

    @InjectMocks
    private AnalyticController analyticController;

    @Mock
    private AnalyticService analyticService;

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private ProfileService profileService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(analyticController).build();
    }

    // base case
    @Test
    @WithMockUser
    void testGetHealthAdvice() {
        String profileId = "testProfileId";
        Profile testProfile = new Profile();
        testProfile.setAge(1);
        List<Profile.MedicalHistory> medlist = new ArrayList<>();
        testProfile.setMedicalHistory(medlist);

        when(profileService.getProfileById(profileId)).thenReturn(Mono.just(testProfile));
        when(prescriptionService.getPrescriptionsByProfileId(profileId)).thenReturn(Flux.empty());
        when(analyticService.getHealthAdvice(anyString())).thenReturn("Mocked Response");

        try {
            mockMvc.perform(get("/analytics/{profileId}", profileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Mocked Response"));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }

        verify(profileService, times(1)).getProfileById(profileId);
        verify(prescriptionService, times(1)).getPrescriptionsByProfileId(profileId);
        verify(analyticService, times(1)).getHealthAdvice(anyString());
    }


    // profileid null case
    @Test
    @WithMockUser
    void testGetHealthAdviceFail() {
        String profileId = "testProfileId";

        when(profileService.getProfileById(profileId)).thenReturn(Mono.empty());
        when(prescriptionService.getPrescriptionsByProfileId(profileId)).thenReturn(Flux.empty());
        when(analyticService.getHealthAdvice(anyString())).thenReturn("Mocked Response");

        try {
            mockMvc.perform(get("/analytics/{profileId}", profileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }

        verify(profileService, times(1)).getProfileById(profileId);
        verify(prescriptionService, times(1)).getPrescriptionsByProfileId(profileId);
        verify(analyticService, never()).getHealthAdvice(anyString());
    }
}
