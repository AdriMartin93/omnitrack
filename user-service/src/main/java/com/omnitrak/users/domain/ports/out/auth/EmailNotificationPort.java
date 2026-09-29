package com.omnitrak.users.domain.ports.out.auth;

public interface EmailNotificationPort {
    void sendPasswordResetEmail(String email, String resetToken);
    void sendAccountActivationEmail(String email, String resetToken);
}
