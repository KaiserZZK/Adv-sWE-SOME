package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.User;
import com.advswesome.advswesome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
        Boolean isEmailOrUsernameTaken = userService.isEmailOrUsernameTaken(user.getEmail(), user.getAccountname());

        if (!isEmailOrUsernameTaken) {
            return userService.createUser(user);
        } else {
            // TODO error-handling: "email or username in use"
            return Mono.empty();
        }

    }

    @PostMapping("/auth/login")
    public String authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
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
