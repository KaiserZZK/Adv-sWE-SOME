package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ProfileRepository;
import com.advswesome.advswesome.repository.document.Profile;
import jakarta.annotation.PostConstruct;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnalyticService {



    @Value("${openai.api.key}")
    private String openaiApiKey;


//    @Value("${OPENAI_API_KEY}")
//    private String openaiKey;
//
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines";



    public String getHealthAdvice(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

//        String openAIKey = "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";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        HttpEntity<String> request = new HttpEntity<>(createRequestBody(prompt), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

        return response.getBody();


    }

    private String createRequestBody(String prompt) {
        // Specify the GPT-3.5 model in the request body
        return "{ \"model\": \"gpt-3.5-turbo\", \"prompt\": \"" + prompt + "\", \"max_tokens\": 256 }";
    }

}
