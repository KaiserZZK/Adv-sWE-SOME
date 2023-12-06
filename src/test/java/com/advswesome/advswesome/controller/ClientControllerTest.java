package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.service.ClientService;
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

public class ClientControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(clientController).build();
    }

    @Test
    void testCreateClient() {
        Client client = new Client();
        client.setClientId("123");
        client.setAppName("TestApp");
        client.setClientType("INDIVIDUAL");

        when(clientService.createClient(any(Client.class))).thenReturn(Mono.just(client));

        webTestClient.post()
                .uri("/clients/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(client)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.clientId").isEqualTo("123")
                .jsonPath("$.appName").isEqualTo("TestApp")
                .jsonPath("$.clientType").isEqualTo("INDIVIDUAL");
    }

    @Test
    void testGetClientById() {
        Client client = new Client();
        client.setClientId("123");
        client.setAppName("TestApp");
        client.setClientType("INDIVIDUAL");

        when(clientService.getClientById("123")).thenReturn(Mono.just(client));

        webTestClient.get()
                .uri("/clients/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.clientId").isEqualTo("123")
                .jsonPath("$.appName").isEqualTo("TestApp")
                .jsonPath("$.clientType").isEqualTo("INDIVIDUAL");
    }
}
