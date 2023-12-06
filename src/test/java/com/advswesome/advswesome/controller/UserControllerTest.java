package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.User;
import com.advswesome.advswesome.service.AuthService;
import com.advswesome.advswesome.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserController userController;
    private ObjectMapper objectMapper = new ObjectMapper();

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(userController).build();
    }

    @Test
    void createUserTest() throws Exception {
        User mockUser = new User();
        mockUser.setUserId("123");
        mockUser.setEmail("123@122.com");
        mockUser.setClientId("1");

        when(userService.createUser(any(User.class))).thenReturn(Mono.just(mockUser));

        webTestClient.post()
                .uri("/users/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mockUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo("123")
                .jsonPath("$.email").isEqualTo("123@122.com")
                .jsonPath("$.clientId").isEqualTo("1");
    }
}
