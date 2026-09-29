package com.omnitrak.users.domain.ports.in.auth;

import com.omnitrak.users.domain.models.User;

public interface RegisterUserUseCase {
    User registerUser(String username, String email, String password);
}
