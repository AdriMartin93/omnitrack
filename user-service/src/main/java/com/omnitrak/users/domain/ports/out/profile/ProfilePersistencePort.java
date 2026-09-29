package com.omnitrak.users.domain.ports.out.profile;

import com.omnitrak.users.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface ProfilePersistencePort {

    Optional<User> findById(UUID userId);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User save(User user);


}
