package com.omnitrak.users.infrastructure.adapters.security;

import com.omnitrak.users.domain.ports.out.auth.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

        private final PasswordEncoder passwordEncoder;

        public BcryptPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }


        @Override
        public String encode(CharSequence rawPassword) {
            return passwordEncoder.encode(rawPassword);
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }

}
