package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients/")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping("/{clientId}")
    public Mono<Client> getClientById(@PathVariable String clientId) {
        return clientService.getClientById(clientId);
    }

}
