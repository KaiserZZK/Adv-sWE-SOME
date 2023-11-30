package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.LoginRequest;
import com.advswesome.advswesome.repository.document.LoginResponse;
import com.advswesome.advswesome.repository.document.User;
import com.advswesome.advswesome.service.UserService;
import com.auth0.jwt.exceptions.JWTCreationException;

import lombok.RequiredArgsConstructor;
import com.advswesome.advswesome.service.AuthService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/auth/register")
    public Mono<User> createUser(@RequestBody User user) {
        Boolean isEmailOrUsernameTaken = userService.isEmailOrUsernameTaken(user.getEmail(), user.getUsername());
        if (!isEmailOrUsernameTaken) {
            return userService.createUser(user);
        } else {
            return Mono.empty();
        }
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/admin/test/clear")
    public Mono<Void> removeAllUsers() {
        return  userService.removeAllUsers();
    }

}
