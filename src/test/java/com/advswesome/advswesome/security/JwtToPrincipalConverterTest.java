package com.advswesome.advswesome.security;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtToPrincipalConverterTest {

    @Test
    public void testConvert() {
        DecodedJWT jwt = mock(DecodedJWT.class);

        var emailClaim = mock(com.auth0.jwt.interfaces.Claim.class);
        when(emailClaim.asString()).thenReturn("user@example.com");
        when(jwt.getClaim("e")).thenReturn(emailClaim);

        var authorityClaim = mock(com.auth0.jwt.interfaces.Claim.class);
        when(authorityClaim.isNull()).thenReturn(false);
        when(authorityClaim.asList(String.class)).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));
        when(jwt.getClaim("au")).thenReturn(authorityClaim);

        when(jwt.getSubject()).thenReturn("user123");

        JwtToPrincipalConverter converter = new JwtToPrincipalConverter();
        UserPrincipal userPrincipal = converter.convert(jwt);

        assertEquals("user123", userPrincipal.getUserId());
        assertEquals("user@example.com", userPrincipal.getEmail());
        assertEquals(2, userPrincipal.getAuthorities().size());
    }

    @Test
    public void testConvert_WithNullClaim() {
        // Create a mock DecodedJWT
        DecodedJWT jwt = Mockito.mock(DecodedJWT.class);

        // Mock the behavior of jwt.getClaim(claim).isNull() to return true
        Claim claim = Mockito.mock(Claim.class);
        var emailClaim = mock(com.auth0.jwt.interfaces.Claim.class);
        when(emailClaim.asString()).thenReturn("user@example.com");
        when(jwt.getClaim("e")).thenReturn(emailClaim);

        Mockito.when(jwt.getClaim("au")).thenReturn(claim);
        Mockito.when(claim.isNull()).thenReturn(true);

        // Create an instance of JwtToPrincipalConverter
        JwtToPrincipalConverter converter = new JwtToPrincipalConverter();

        // Call the convert method
        UserPrincipal userPrincipal = converter.convert(jwt);

        // Verify that the returned UserPrincipal has an empty authorities list
        assertEquals(0, userPrincipal.getAuthorities().size());
    }
}
