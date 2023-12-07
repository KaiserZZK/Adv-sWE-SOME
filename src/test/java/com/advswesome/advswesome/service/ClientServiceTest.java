package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ClientRepository;
import com.advswesome.advswesome.repository.document.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void createClientSuccess() {
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(Mono.just(client));

        StepVerifier.create(clientService.createClient(client))
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository).save(client);
    }

    @Test
    void getClientByIdSuccess() {
        String id = "client-id";
        Client client = new Client();
        when(clientRepository.findById(id)).thenReturn(Mono.just(client));

        StepVerifier.create(clientService.getClientById(id))
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository).findById(id);
    }

    @Test
    void getClientByIdNotFound() {
        String id = "non-existing-id";
        when(clientRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.getClientById(id))
                .verifyComplete();

        verify(clientRepository).findById(id);
    }

    @Test
    void createClientException() {
        Client client = new Client();
        RuntimeException exception = new RuntimeException("Database error");
        when(clientRepository.save(client)).thenReturn(Mono.error(exception));

        StepVerifier.create(clientService.createClient(client))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Database error"))
                .verify();

        verify(clientRepository).save(client);
    }

    @Test
    void getClientByIdException() {
        String id = "client-id";
        RuntimeException exception = new RuntimeException("Database error");
        when(clientRepository.findById(id)).thenReturn(Mono.error(exception));

        StepVerifier.create(clientService.getClientById(id))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Database error"))
                .verify();

        verify(clientRepository).findById(id);
    }
}
