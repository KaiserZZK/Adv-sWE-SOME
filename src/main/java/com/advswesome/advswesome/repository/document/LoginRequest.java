package com.advswesome.advswesome.repository.document;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    private final String clientId;
    private final String email;
    private final String password;
}
