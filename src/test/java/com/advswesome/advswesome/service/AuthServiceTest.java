package com.advswesome.advswesome.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.advswesome.advswesome.repository.document.LoginResponse;
import com.advswesome.advswesome.security.JwtIssuer;
import com.advswesome.advswesome.security.UserPrincipal;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class AuthServiceTest {

    @Mock
    private JwtIssuer jwtIssuer;
    @Mock
    private AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        jwtIssuer = mock(JwtIssuer.class);
        authenticationManager = mock(AuthenticationManager.class);
        authService = new AuthService(jwtIssuer, authenticationManager);
    }

    @Test
    void attemptLoginSuccessful() throws Exception {
        String email = "test@example.com";
        String password = "password";
        String userId = "123";
        String token = "jwt.token.here";

        UserPrincipal principal = UserPrincipal.builder().userId(userId).email(email).password(password).authorities(new ArrayList<>()).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtIssuer.issue(any())).thenReturn(token);

        LoginResponse response = authService.attemptLogin(email, password);

        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void attemptLoginFailure() {
        String invalidEmail = "wrong@example.com";
        String invalidPassword = "wrongPassword";

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authService.attemptLogin(invalidEmail, invalidPassword));
    }

    @Test
    void attemptLoginWithJWTCreationException() throws Exception {
        String email = "test@example.com";
        String password = "password";
        String userId = "123";

        UserPrincipal principal = UserPrincipal.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .authorities(Collections.emptyList())
                .build();
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(principal);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(jwtIssuer.issue(any())).thenThrow(new JWTCreationException("Error creating JWT", new Exception()));

        assertThrows(JWTCreationException.class, () -> authService.attemptLogin(email, password));
    }

    @Test
    void attemptLoginWithDifferentRoles() throws Exception {
        String email = "admin@example.com";
        String password = "adminPassword";
        String userId = "admin123";
        String token = "jwt.admin.token";
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

        UserPrincipal principal = UserPrincipal.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .authorities(roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()))
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtIssuer.issue(any())).thenReturn(token);

        LoginResponse response = authService.attemptLogin(email, password);

        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void attemptLoginWithEmptyCredentials() {
        // Test with empty email and password
        assertThrows(Exception.class, () -> authService.attemptLogin("", ""));

        // Test with null email and password
        assertThrows(Exception.class, () -> authService.attemptLogin(null, null));
    }


    @Test
    void attemptLoginWithUnsupportedEncodingException() throws Exception {
        String email = "test@example.com";
        String password = "password";
        UserPrincipal principal = setupUserPrincipal(email, password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtIssuer.issue(any())).thenThrow(new UnsupportedEncodingException("Error encoding JWT"));


        assertThrows(UnsupportedEncodingException.class, () -> authService.attemptLogin(email, password));
    }

    @Test
    void attemptLoginWithIllegalArgumentException() throws Exception {
        String email = "test@example.com";
        String password = "password";
        UserPrincipal principal = setupUserPrincipal(email, password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtIssuer.issue(any())).thenThrow(new IllegalArgumentException("Invalid argument for JWT"));

        assertThrows(IllegalArgumentException.class, () -> authService.attemptLogin(email, password));
    }

    private UserPrincipal setupUserPrincipal(String email, String password) {
        return UserPrincipal.builder()
                .userId("123")
                .email(email)
                .password(password)
                .authorities(Collections.emptyList())
                .build();
    }

}
