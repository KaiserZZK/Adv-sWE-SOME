package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.UserRepository;
import com.advswesome.advswesome.repository.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.advswesome.advswesome.security.AuthenticationResponse;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
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

    public AuthenticationResponse authenticateUser(User user) { // TODO throws UserNotFoundException
        User foundUser = this.getUserByEmail(user.getEmail());
        String foundPassword = foundUser.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Boolean match = encoder.matches(user.getPassword(), foundPassword);

        if (match) {
            // return "your JWT token: ";
        } else {
            // return "plz check your email or password";
        }

        return new AuthenticationResponse("foo", "bar");
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
