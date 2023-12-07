package com.advswesome.advswesome;

import com.advswesome.advswesome.repository.document.LoginRequest;
import com.advswesome.advswesome.repository.document.LoginResponse;
import com.advswesome.advswesome.repository.document.Profile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiClientIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static String client1Id;
    private static String client2Id;
    private static String user1email;
    private static String user1password;
    private static String user1Id;

    private static String user1Token;

    private static String user2email;
    private static String user2password;
    private static String user2Id;
    private static String user2Token;

    private static String user1ProfileId;

    private static String user2ProfileId;



    @BeforeAll
    static void setup() {
        client1Id = "uO6alpM2c9OPQl1bNYwu";
        client2Id = "LnvxTLc1DqotgvcTbnMQ";
        user1email = "mtc-testing-user-1@example.com";
        user1password = "12345678";
        user1Id = "6umzJtEPCSbKEWPgj61m";
        user2email = "mtc-testing-user-2@example.com";
        user2password = "12345678";
        user2Id = "b537rLk3ehZEI0epwdiP";
    }

    @Test
    void testUserDataIsolation() {
        LoginResponse loginResponse1 = webTestClient.post()
                .uri("/users/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LoginRequest.builder().email(user1email).password(user1password).clientId(client1Id).build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(LoginResponse.class)
                .getResponseBody()
                .blockFirst();

        user1Token = loginResponse1.getToken();

        LoginResponse loginResponse2 = webTestClient.post()
                .uri("/users/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LoginRequest.builder().email(user2email).password(user2password).clientId(client2Id).build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(LoginResponse.class)
                .getResponseBody()
                .blockFirst();

        user2Token = loginResponse2.getToken();

        user1ProfileId = createProfileAndGetId(user1Id, user1Token);
        user2ProfileId = createProfileAndGetId(user2Id, user2Token);

        // Test if User 1 can access User 1's profile (should be able to)
        webTestClient.get()
                .uri("/profiles/" + user1ProfileId)
                .header("Authorization", "Bearer " + user1Token)
                .exchange()
                .expectStatus().isOk();

        // Test if User 1 can access User 2's profile (should not be able to)
        webTestClient.get()
                .uri("/profiles/" + user2ProfileId)
                .header("Authorization", "Bearer " + user1Token)
                .exchange()
                .expectStatus().isForbidden();

        // Test if User 2 can access User 2's profile (should be able to)
        webTestClient.get()
                .uri("/profiles/" + user2ProfileId)
                .header("Authorization", "Bearer " + user2Token)
                .exchange()
                .expectStatus().isOk();

        // Test if User 2 can access User 1's profile (should not be able to)
        webTestClient.get()
                .uri("/profiles/" + user1ProfileId)
                .header("Authorization", "Bearer " + user2Token)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void testClientDataIsolation() {
        // User 1 should not login with client 2
        webTestClient.post()
                .uri("/users/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LoginRequest.builder().email(user1email).password(user1password).clientId(client2Id).build())
                .exchange()
                .expectStatus().isUnauthorized();

        // User 2 should not login with client 1
        webTestClient.post()
                .uri("/users/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(LoginRequest.builder().email(user2email).password(user2password).clientId(client1Id).build())
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @AfterEach
    void cleanup() {
        // Delete User 1's profile
        if (user1Token != null && user1ProfileId != null) {
            webTestClient.delete()
                    .uri("/profiles/" + user1ProfileId)
                    .header("Authorization", "Bearer " + user1Token);
        }

        // Delete User 2's profile
        if (user2Token != null && user2ProfileId != null) {
            webTestClient.delete()
                    .uri("/profiles/" + user2ProfileId)
                    .header("Authorization", "Bearer " + user2Token);
        }
    }

    private String createProfileAndGetId(String userId, String token) {
        Profile profile = new Profile();
        profile.setUserId(userId);
        return webTestClient.post()
                .uri("/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .bodyValue(profile)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Profile.class)
                .getResponseBody()
                .map(Profile::getProfileId)
                .blockFirst();
    }
}
