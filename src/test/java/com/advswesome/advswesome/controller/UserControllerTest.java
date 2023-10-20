// package com.advswesome.advswesome.controller;

// import com.advswesome.advswesome.repository.document.User;
// import com.advswesome.advswesome.service.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;

// import static org.mockito.Mockito.*;

// class UserControllerTest {

//     @InjectMocks
//     private UserController userController;

//     @Mock
//     private UserService userService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void getAllUsers() {
//         when(userService.getAllUsers()).thenReturn(Flux.just(new User("test@email.com")));

//         userController.getAllUsers().collectList().block();

//         verify(userService, times(1)).getAllUsers();
//     }

//     @Test
//     void createUser() {
//         User user = new User("test@email.com");
//         when(userService.saveUser(user)).thenReturn(Mono.just(user));

//         userController.createUser(user).block();

//         verify(userService, times(1)).saveUser(user);
//     }

//     @Test
//     void getUserById() {
//         User user = new User("test@email.com");
//         user.setUserId("123");
//         when(userService.getUserById("123")).thenReturn(Mono.just(user));

//         userController.getUserById("123").block();

//         verify(userService, times(1)).getUserById("123");
//     }
// }
