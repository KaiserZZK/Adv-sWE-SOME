// package com.advswesome.advswesome.service;

// import com.advswesome.advswesome.repository.UserRepository;
// import com.advswesome.advswesome.repository.document.User;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;

// import static org.mockito.Mockito.*;

// class UserServiceTest {

//     @InjectMocks
//     private UserService userService;

//     @Mock
//     private UserRepository userRepository;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void getAllUsers() {
//         when(userRepository.findAll()).thenReturn(Flux.just(new User("test@email.com")));

//         userService.getAllUsers().collectList().block();

//         verify(userRepository, times(1)).findAll();
//     }

//     @Test
//     void saveUser() {
//         User user = new User("test@email.com");
//         when(userRepository.save(user)).thenReturn(Mono.just(user));

//         userService.saveUser(user).block();

//         verify(userRepository, times(1)).save(user);
//     }

//     @Test
//     void getUserById() {
//         User user = new User("test@email.com");
//         user.setUserId("123");
//         when(userRepository.findById("123")).thenReturn(Mono.just(user));

//         userService.getUserById("123").block();

//         verify(userRepository, times(1)).findById("123");
//     }
// }
