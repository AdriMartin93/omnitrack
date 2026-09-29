package com.omnitrak.users.domain.ports.in.auth;

public interface ActivateAccountUseCase {
    void activate(String token);
}
