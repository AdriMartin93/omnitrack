package com.omnitrak.users.domain.ports.in.auth;

import java.util.UUID;

public interface LogoutUserUseCase {
    void logout(UUID userId, String refreshToken);
}
