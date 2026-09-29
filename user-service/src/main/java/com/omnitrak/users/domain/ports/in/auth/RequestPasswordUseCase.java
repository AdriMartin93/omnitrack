package com.omnitrak.users.domain.ports.in.auth;

public interface RequestPasswordUseCase {
    void requestPasswordReset(String email);
}
