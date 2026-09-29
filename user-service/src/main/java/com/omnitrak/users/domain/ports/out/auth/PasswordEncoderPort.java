package com.omnitrak.users.domain.ports.out.auth;

public interface PasswordEncoderPort {

    boolean matches(CharSequence rawPassword, String encodedPassword);

    String encode(CharSequence rawPassword);
}
