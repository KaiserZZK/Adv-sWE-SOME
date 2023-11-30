package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.document.LoginResponse;
import com.advswesome.advswesome.security.JwtIssuer;
import com.advswesome.advswesome.security.UserPrincipal;
import com.auth0.jwt.exceptions.JWTCreationException;

import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String email, String password) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .userId(principal.getUserId())
                .email(principal.getEmail())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}