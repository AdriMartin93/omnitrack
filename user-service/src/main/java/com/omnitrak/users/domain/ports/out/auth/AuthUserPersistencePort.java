package com.omnitrak.users.domain.ports.out.auth;

import com.omnitrak.users.domain.models.User;

import java.util.Optional;

public interface AuthUserPersistencePort {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
