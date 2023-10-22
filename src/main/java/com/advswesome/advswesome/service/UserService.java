package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.UserRepository;
import com.advswesome.advswesome.repository.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        // TODO 
        // check for existing email/username
        return userRepository.save(user);
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

    public Mono<User> getUserByEmail(String email) {
        Iterable<User> users =  userRepository.findAll().toIterable();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return Mono.just(user);
            }
        }
        return Mono.empty();
    }


    public Mono<Void> removeAllUsers() {
        return userRepository.deleteAll();
    }

}
