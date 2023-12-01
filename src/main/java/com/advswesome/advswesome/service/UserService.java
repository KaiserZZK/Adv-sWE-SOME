package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.UserRepository;
import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.repository.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ClientService clientService;

    @Autowired
    public UserService( 
        UserRepository userRepository, 
        ClientService clientService
    ) {
        this.userRepository = userRepository;
        this.clientService = clientService;
    }

    public Mono<User> createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String role = this.getRoleFromClient(user);
        user.setRole(role);
        return userRepository.save(user);
    }

    public String getRoleFromClient(User user) {
        Mono<Client> monoClient = clientService.getClientById(user.getClientId());
        Mono<String> monoClientType = monoClient.map(client -> {
            return client.getClientType();
        });

        String clientType = monoClientType.block();
        String role = null;
        switch (clientType) {
            case "ORGANIZATION":
                role = "ROLE_STAFF";
                break;
            case "INDIVIDUAL":
                role = "ROLE_USER";
                break;
        }
        return role; 
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Boolean isEmailOrUsernameTaken(String email, String username) {
        Iterable<User> users =  userRepository.findAll().toIterable();
        for (User user : users) {
            if (user.getEmail().equals(email) || user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    } 

    public User getUserByEmail(String email) {
        Iterable<User> users =  userRepository.findAll().toIterable();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Mono<Void> removeAllUsers() {
        return userRepository.deleteAll();
    }

}
