package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ClientRepository;
import com.advswesome.advswesome.repository.document.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Mono<Client> createClient(Client client) {
        return clientRepository.save(client);
    }

    public Mono<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

}
