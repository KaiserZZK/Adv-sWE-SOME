package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.UserRepository;
import com.advswesome.advswesome.repository.document.Client;
import com.advswesome.advswesome.repository.document.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserTest() {
        User user = new User("userId", "clientId", "test@example.com", "username", "password", null, "createdAt", "updatedAt");
        Client client = new Client();
        client.setClientId("clientId");
        client.setClientType("ORGANIZATION");  // or "INDIVIDUAL"

        when(clientService.getClientById(anyString())).thenReturn(Mono.just(client));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        User createdUser = userService.createUser(user).block();

        assertNotNull(createdUser);
         assertEquals("ROLE_STAFF", createdUser.getRole());  // or "ROLE_USER"

        verify(userRepository).save(any(User.class));
        verify(clientService).getClientById(anyString());
    }

    @Test
    void createUserRoleUserTest() {
        User user = new User("userId", "clientId", "test@example.com", "username", "password", null, "createdAt", "updatedAt");
        Client client = new Client();
        client.setClientId("clientId");
        client.setClientType("INDIVIDUAL");

        when(clientService.getClientById(anyString())).thenReturn(Mono.just(client));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        User createdUser = userService.createUser(user).block();

        assertNotNull(createdUser);
        assertEquals("ROLE_USER", createdUser.getRole());

        verify(userRepository).save(any(User.class));
        verify(clientService).getClientById(anyString());
    }


    @Test
    void getUserByIdTest() {
        String userId = "testId";
        User user = new User(userId, "clientId", "test@example.com", "username", "password", null, "createdAt", "updatedAt");

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        User foundUser = userService.getUserById(userId).block();

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getUserId());

        verify(userRepository).findById(userId);
    }

    @Test
    void isEmailOrUsernameTakenTest() {
        String userId = "testId";
        User user = new User(userId, "clientId", "test@example.com", "username", "password", null, "createdAt", "updatedAt");
        Iterable<User> users = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(Flux.fromIterable(users));

        assertTrue(userService.isEmailOrUsernameTaken(user.getEmail(), user.getUsername()));
        assertFalse(userService.isEmailOrUsernameTaken("otherEmail", "otherUsername"));
    }

    @Test
    void getUserByEmailTest() {
        String email = "test@example.com";
        User user = new User("userId", "clientId", email, "username", "password", null, "createdAt", "updatedAt");
        Iterable<User> users = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(Flux.fromIterable(users));

        User foundUser = userService.getUserByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());

        verify(userRepository).findAll();
    }

    @Test
    void removeAllUsersTest() {
        when(userRepository.deleteAll()).thenReturn(Mono.empty());

        userService.removeAllUsers().block();

        verify(userRepository).deleteAll();
    }

    @Test
    void getRoleFromClientWithUnknownTypeTest() {
        User user = new User("userId", "clientId", "test@example.com", "username", "password", null, "createdAt", "updatedAt");
        Client client = new Client();
        client.setClientId("clientId");
        client.setClientType("UNKNOWN_TYPE");

        when(clientService.getClientById(anyString())).thenReturn(Mono.just(client));

        String role = userService.getRoleFromClient(user);
        assertNull(role); // assuming that the default case returns null

        verify(clientService).getClientById(anyString());
    }

    @Test
    void getUserByEmailNotFoundTest() {
        when(userRepository.findAll()).thenReturn(Flux.empty());

        User foundUser = userService.getUserByEmail("nonexistent@example.com");
        assertNull(foundUser);

        verify(userRepository).findAll();
    }

    @Test
    void isEmailOrUsernameTakenNotFoundTest() {
        when(userRepository.findAll()).thenReturn(Flux.empty());

        assertFalse(userService.isEmailOrUsernameTaken("nonexistent@example.com", "nonexistent"));

        verify(userRepository).findAll();
    }

    @Test
    void getUserByIdNotFoundTest() {
        when(userRepository.findById(anyString())).thenReturn(Mono.empty());

        User foundUser = userService.getUserById("nonexistentId").block();
        assertNull(foundUser);

        verify(userRepository).findById(anyString());
    }

    @Test
    void isEmailOrUsernameNotTakenTest() {
        String email = "test@example.com";
        String username = "username";
        User user = new User("userId", "clientId", email, "differentUsername", "password", null, "createdAt", "updatedAt");
        Iterable<User> users = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(Flux.fromIterable(users));

        assertFalse(userService.isEmailOrUsernameTaken("otherEmail@example.com", "otherUsername"));

        verify(userRepository).findAll();
    }




}
