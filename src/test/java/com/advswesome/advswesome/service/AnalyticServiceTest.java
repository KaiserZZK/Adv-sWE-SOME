package com.advswesome.advswesome.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
@SpringBootTest
public class AnalyticServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AnalyticService analyticService;


    @Test
    public void testGetHealthAdvice() {


        String mockResponseReturn = "{\"choices\":[{\"message\":{\"content\":\"Mocked advice\"}}]}";
        ResponseEntity<String> mockResponse = ResponseEntity.ok(mockResponseReturn);
        Mockito.when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(mockResponse);

        // Call the service method
        String result = analyticService.getHealthAdvice("something");
        System.out.println(result);

        // Assert the result
        Assertions.assertTrue(result.contains("Mocked advice"));


        // Verify interactions
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), eq(String.class));
    }


}
