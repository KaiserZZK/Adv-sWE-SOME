package com.advswesome.advswesome.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;


public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    // @JsonProperty("refresh_token")
    // private String refreshToken;

    @Autowired
    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
        // this.refreshToken = refreshToken;
    } 

}