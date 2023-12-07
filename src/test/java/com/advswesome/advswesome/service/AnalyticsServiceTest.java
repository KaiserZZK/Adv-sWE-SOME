package com.advswesome.advswesome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {

    private AnalyticService analyticService;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        analyticService = new AnalyticService();
        restTemplate = mock(RestTemplate.class);
        analyticService.setRestTemplate(restTemplate);
    }

    @Test
    void testGetHealthAdvice_Success() {
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Mocked advice\"}}]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(OPENAI_API_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        String advice = analyticService.getHealthAdvice("Prompt text");

        assertEquals("Mocked advice", advice);
    }

    @Test
    void testGetHealthAdvice_NoAdviceFound() {
        String mockResponse = "{\"choices\":[]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(OPENAI_API_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        String advice = analyticService.getHealthAdvice("Prompt text");

        assertEquals("No advice found", advice);
    }

    @Test
    void testGetHealthAdvice_EmptyResponse() {
        String mockResponse = "";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(OPENAI_API_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        String advice = analyticService.getHealthAdvice("Prompt text");

        assertEquals("No advice found", advice);
    }

    @Test
    void testGetHealthAdvice_ErrorParsingResponse() {
        String mockInvalidResponse = "Invalid JSON";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockInvalidResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(OPENAI_API_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        String advice = analyticService.getHealthAdvice("Prompt text");

        assertEquals("Error parsing response", advice);
    }
}
