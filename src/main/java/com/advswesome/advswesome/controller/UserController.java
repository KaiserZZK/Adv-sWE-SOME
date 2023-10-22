package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.User;
import com.advswesome.advswesome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/register")
    public Mono<User> createUser(@RequestBody User user) {
        Boolean isEmailOrUsernameTaken = userService.isEmailOrUsernameTaken(user.getEmail(), user.getUsername());

        if (!isEmailOrUsernameTaken) {
            return userService.createUser(user);
        } else {
            // TODO error-handling: "email or username in use"
            return Mono.empty();
        }

    }

    @PostMapping("/auth/login")
    public Mono<User> loginUser(@RequestBody LoginRequest loginRequest) {
        Mono<User> requester = userService.getUserByEmail( loginRequest.getEmail() );
        // TODO user email  not found?

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Mono<String> userPassword = requester.flatMap(u -> {
            System.out.println(u.getEmail());
            System.out.println(u.getUsername());
            return Mono.just(u.getStoredHash());
        });

        System.out.println("submitted request" + loginRequest.getPassword() );
        System.out.println("stored hash" + userPassword.block() );
        String hashed =  bCryptPasswordEncoder.encode(loginRequest.getPassword()); 
        System.out.println("hashed" + hashed);
        Boolean match = bCryptPasswordEncoder.matches(loginRequest.getPassword(), userPassword.block());

        System.out.println(match);
        return Mono.empty();
    } 

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/admin/test/clear")
    public Mono<Void> removeAllUsers() {
        return  userService.removeAllUsers();
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

    }

}
