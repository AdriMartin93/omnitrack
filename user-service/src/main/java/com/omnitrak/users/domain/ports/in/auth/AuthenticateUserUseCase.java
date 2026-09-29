package com.omnitrak.users.domain.ports.in.auth;

import com.omnitrak.users.domain.models.AuthTokens;

public interface AuthenticateUserUseCase {
    AuthTokens login(String username, String password);
}
