package com.advswesome.advswesome.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtDecoder jwtDecoder;

    @Mock
    private JwtToPrincipalConverter jwtToPrincipalConverter;

    @Mock
    private DecodedJWT decodedJWT;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtDecoder, jwtToPrincipalConverter);
    }

    @Test
    void testDoFilterInternal_SuccessfulAuthentication() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock the token extraction from the request
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");

        // Mock JWT decoding and conversion to UserPrincipal
        when(jwtDecoder.decode("validToken")).thenReturn(decodedJWT);
        when(jwtToPrincipalConverter.convert(decodedJWT)).thenReturn(UserPrincipal.builder().build());

        // Call the doFilterInternal method
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filterChain was called
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_AuthenticationFailure() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock the token extraction from the request
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");

        // Mock JWT decoding throwing an exception
        when(jwtDecoder.decode("invalidToken")).thenThrow(JWTVerificationException.class);

        // Call the doFilterInternal method
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filterChain was called
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_EmptyToken() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock an empty token in the request
        when(request.getHeader("Authorization")).thenReturn(null);

        // Call the doFilterInternal method
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filterChain was called
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_UnexpectedException() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock the token extraction from the request
        when(request.getHeader("Authorization")).thenReturn("Bearer token");

        // Mock JWT decoding throwing an unexpected exception
        when(jwtDecoder.decode("token")).thenThrow(UnsupportedEncodingException.class);

        // Call the doFilterInternal method
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filterChain was called
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_Invalid_No_Bearer() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock the token extraction from the request
        when(request.getHeader("Authorization")).thenReturn( "token");

        // Mock JWT decoding throwing an unexpected exception
        when(jwtDecoder.decode("token")).thenThrow(UnsupportedEncodingException.class);

        // Call the doFilterInternal method
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filterChain was called
        verify(filterChain, times(1)).doFilter(request, response);
    }

}
