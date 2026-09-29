package com.omnitrak.users.domain.ports.in.auth;

public interface ResetPasswordUseCase {
    void resetPassword(String token, String newPassword);
}
