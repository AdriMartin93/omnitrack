package com.omnitrak.users.domain.ports.in.account;

import java.util.UUID;

public interface ChangeEmailUseCase {
    void changeEmail(UUID userId, String newEmail, String password);
}

