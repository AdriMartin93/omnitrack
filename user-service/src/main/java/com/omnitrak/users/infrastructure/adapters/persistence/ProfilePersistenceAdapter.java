package com.omnitrak.users.infrastructure.adapters.persistence;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.out.profile.ProfilePersistencePort;
import com.omnitrak.users.infrastructure.entities.UserEntity;
import com.omnitrak.users.infrastructure.mappers.UserEntityMapper;
import com.omnitrak.users.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class ProfilePersistenceAdapter implements ProfilePersistencePort {

    private final JpaUserRepository userRepository;
    private final UserEntityMapper userMapper;

    public ProfilePersistenceAdapter(JpaUserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(UUID userId){
        return userRepository.findById(userId)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }


}
