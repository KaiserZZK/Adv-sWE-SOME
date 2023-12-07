package com.advswesome.advswesome.service;

// import com.advswesome.advswesome.repository.ProfileRepository;
// import com.advswesome.advswesome.repository.document.Profile;
// import jakarta.annotation.PostConstruct;
// import org.apache.http.Header;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Component
public class AnalyticService {



    @Value("${openai.api.key}")
    private String openaiApiKey;


    //    @Value("${OPENAI_API_KEY}")
//    private String openaiKey;
//
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";


    public String getHealthAdvice(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        HttpEntity<String> request = new HttpEntity<>(createRequestBody(prompt), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode choicesNode = rootNode.path("choices");

            if (!choicesNode.isArray() || choicesNode.isEmpty()) {
                return "No advice found";
            }

            JsonNode firstChoiceNode = choicesNode.get(0);
            JsonNode messageNode = firstChoiceNode.path("message");
            JsonNode contentNode = messageNode.path("content");

            return contentNode.asText();
        } catch (Exception e) {
//            e.printStackTrace();
            return "Error parsing response";
        }

//        return createRequestBody(prompt);

    }

    private String createRequestBody(String prompt) {
        // Specify the GPT-3.5 model in the request body
//        return "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [\"" + "{" + "role\"prompt + "\" ";
        return "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]}";
    }

}
