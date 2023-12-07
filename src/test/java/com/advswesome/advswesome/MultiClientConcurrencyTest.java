package com.advswesome.advswesome;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.security.JwtIssuer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MultiClientConcurrencyTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JwtIssuer jwtIssuer;

    @Test
    void testConcurrentProfileCreation(){
        int numberOfConcurrentRequests = 10;
        List<CompletableFuture<Profile>> profileCreationFutures = new ArrayList<>();

        for (int i = 0; i < numberOfConcurrentRequests; i++) {
            String userId = "user" + i; // Unique userId for each request
            String profileId = "profile" + i;
            CompletableFuture<Profile> profileCreationFuture = CompletableFuture.supplyAsync(() -> {
                Profile profile = new Profile();
                profile.setUserId(userId);
                profile.setProfileId(profileId);
                String token = "";
                try {
                    token = jwtIssuer.issue(JwtIssuer.Request.builder()
                            .userId(userId)
                            .email("")
                            .roles(Arrays.asList("ROLE_USER"))
                            .build());
                } catch (Exception e) {
                    fail(e.getMessage());
                }
                return webTestClient.post()
                        .uri("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .bodyValue(profile)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(Profile.class)
                        .getResponseBody()
                        .blockFirst();

            });

            profileCreationFutures.add(profileCreationFuture);
        }

        // Wait for all profile creation requests to complete
        CompletableFuture.allOf(profileCreationFutures.toArray(new CompletableFuture[0])).join();

        // Assertions to get right response to each caller concurrently
        for (CompletableFuture<Profile> future : profileCreationFutures) {
            Profile createdProfile = future.join();
            assert createdProfile != null : "Profile creation failed";
            assert createdProfile.getUserId().startsWith("user") : "Profile associated with incorrect user";
        }
    }
}
