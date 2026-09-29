package com.omnitrak.users.domain.ports.in.account;

import java.util.UUID;

public interface ChangePasswordUseCase {
    void changePassword(UUID userId, String oldPassword, String newPassword);
}
