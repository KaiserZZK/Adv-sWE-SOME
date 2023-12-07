package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ConsentRepository;
import com.advswesome.advswesome.repository.document.Consent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AnalyticServiceTest {

    @InjectMocks
    private AnalyticService analyticService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @MockBean
//    private RestTemplate restTemplate;
    @Mock
    private RestTemplate restTemplate;


    @Test
    public void testGetHealthAdvice() {

//        RestTemplate restTemplate = new RestTemplate();
        // Mock the RestTemplate's response
        ResponseEntity<String> mockResponse = ResponseEntity.ok("{\"advice_1\":\"Advice1\",\"advice_2\":\"Advice2\",\"advice_3\":\"Advice3\"}");
        Mockito.when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(mockResponse);

        // Call the service method
        String result = analyticService.getHealthAdvice("condition");

        // Assert the result
        Assertions.assertTrue(result.contains("Advice1"));
        Assertions.assertTrue(result.contains("Advice2"));
        Assertions.assertTrue(result.contains("Advice3"));

        // Verify interactions
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), eq(String.class));
    }
}
