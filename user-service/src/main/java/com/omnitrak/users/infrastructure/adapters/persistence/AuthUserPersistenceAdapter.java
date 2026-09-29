package com.omnitrak.users.infrastructure.adapters.persistence;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.out.auth.AuthUserPersistencePort;
import com.omnitrak.users.infrastructure.mappers.UserEntityMapper;
import com.omnitrak.users.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuthUserPersistenceAdapter implements AuthUserPersistencePort {

    private final JpaUserRepository userRepository;
    private final UserEntityMapper userMapper;

    public AuthUserPersistenceAdapter(JpaUserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
