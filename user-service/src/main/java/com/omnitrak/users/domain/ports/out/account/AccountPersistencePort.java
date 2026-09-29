package com.omnitrak.users.domain.ports.out.account;

import com.omnitrak.users.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface AccountPersistencePort {

    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User save(User user);

    void deleteById(UUID userId);
}
