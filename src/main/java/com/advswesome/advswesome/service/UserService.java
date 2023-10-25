package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.UserRepository;
import com.advswesome.advswesome.service.ClientService;
import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.repository.document.User;
import com.advswesome.advswesome.repository.document.Role;
import com.advswesome.advswesome.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.advswesome.advswesome.security.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ClientService clientService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService( 
        UserRepository userRepository, 
        ClientService clientService,
        JwtService jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.clientService = clientService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Mono<User> createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = this.getRoleFromClient(user);
        user.setRole(role);
        return userRepository.save(user);
    }

    public Role getRoleFromClient(User user) {
        Mono<Client> monoClient = clientService.getClientById(user.getClientId());
        Mono<String> monoClientType = monoClient.map(client -> {
            return client.getClientType();
        });

        String clientType = monoClientType.block();
        Role role = null;
        switch (clientType) {
            case "ORGANIZATION":
                role = Role.ORGANIZATION;
                break;
            case "INDIVIDUAL":
                role = Role.INDIVIDUAL;
                break;
        }
        return role; 
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Boolean isEmailOrUsernameTaken(String email, String username) {
        // TODO use hashmap instead of list iteration
        Iterable<User> users =  userRepository.findAll().toIterable();
        for (User user : users) {
            if (user.getEmail().equals(email) || user.getAccountname().equals(username)) {
                return true;
            }
        }
        return false;
    } 

    public AuthenticationResponse authenticateUser(User user) { 
        User foundUser = this.getUserByEmail(user.getEmail());
        String foundPassword = foundUser.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Boolean match = encoder.matches(user.getPassword(), foundPassword);

        if (match) {
            // return JWT token
            var jwtToken = jwtService.generateToken(foundUser);
            System.out.println("***************************" + foundUser.getRole());
            return new AuthenticationResponse(jwtToken); 
        } else {
            return new AuthenticationResponse("Invalid email or incorrect password; please check.");
        }
    }

    public User getUserByEmail(String email) {
        // TODO use hashmap instead of list iteration
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
