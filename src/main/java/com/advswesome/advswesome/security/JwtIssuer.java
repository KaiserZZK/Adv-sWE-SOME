package com.advswesome.advswesome.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(Request request) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {
        var now = Instant.now();

        List<String> rolesList = request.getRoles();
        String[] rolesArr = new String [rolesList.size()];
        rolesList.toArray(rolesArr);

        return JWT.create()
                .withSubject(String.valueOf(request.userId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(properties.getTokenDuration())))
                .withClaim("e", request.getEmail())
                .withArrayClaim("au", rolesArr)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    @Getter
    @Builder
    public static class Request {
        private final String userId;
        private final String email;
        private final List<String> roles;
        }
}
