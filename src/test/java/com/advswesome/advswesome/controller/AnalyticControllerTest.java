package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.service.AnalyticService;
import com.advswesome.advswesome.service.AnalyticServiceTest;
import com.advswesome.advswesome.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyticControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private AnalyticService analyticService;

    @InjectMocks
    private AnalyticController analyticController;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(analyticController).build();
    }

    @Test
    public void testGetHealthAdvice() throws Exception {
        String mockResponse = "Mocked health advice";

        when(analyticService.getHealthAdvice("testId")).thenReturn(mockResponse);

        // Mock the service's response
//        String mockResponse = "Mocked health advice";
//        Mockito.when(analyticService.getHealthAdvice(Mockito.anyString())).thenReturn(mockResponse);

        // Perform the request and assert the response
        mockMvc.perform(get("/analytics/profileId")
                        .contentType("application/json")
                        .content("{\"userHealthCondition\":\"condition\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));
    }
}
