package com.advswesome.advswesome.repository.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
