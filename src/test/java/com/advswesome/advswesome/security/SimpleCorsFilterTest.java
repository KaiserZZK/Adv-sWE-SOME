package com.advswesome.advswesome.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SimpleCorsFilterTest {
    @Test
    void testDoFilter() throws IOException, ServletException {
        // Create a SimpleCorsFilter instance
        SimpleCorsFilter corsFilter = new SimpleCorsFilter();

        // Create mock objects for HttpServletRequest, HttpServletResponse, and FilterChain
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        // Set the request method to "OPTIONS"
        request.setMethod("OPTIONS");

        // Call the doFilter method
        corsFilter.doFilter(request, response, filterChain);

        // Assert that the response headers are set correctly
        assertEquals(request.getHeader("origin"), response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("POST, GET, PUT, OPTIONS, DELETE", response.getHeader("Access-Control-Allow-Methods"));
        assertEquals("3600", response.getHeader("Access-Control-Max-Age"));
        assertEquals("*", response.getHeader("Access-Control-Allow-Headers"));
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

    }

    @Test
    void testDoFilterNonOptionsRequest() throws IOException, ServletException {
        // Create a SimpleCorsFilter instance
        SimpleCorsFilter corsFilter = new SimpleCorsFilter();

        // Create mock objects for HttpServletRequest, HttpServletResponse, and FilterChain
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        // Set the request method to "GET" (a non-OPTIONS request)
        request.setMethod("GET");

        // Call the doFilter method
        corsFilter.doFilter(request, response, filterChain);

        // Verify that the filterChain.doFilter method was called for a non-OPTIONS request
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
