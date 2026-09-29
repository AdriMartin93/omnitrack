package com.omnitrak.users.infrastructure.adapters.persistence;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.out.account.AccountPersistencePort;
import com.omnitrak.users.infrastructure.entities.UserEntity;
import com.omnitrak.users.infrastructure.mappers.UserEntityMapper;
import com.omnitrak.users.infrastructure.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class AccountPersistenceAdapter implements AccountPersistencePort {

    private final JpaUserRepository userRepository;
    private final UserEntityMapper userMapper;

    public AccountPersistenceAdapter(JpaUserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(UUID userId){
        return userRepository.findById(userId)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

}
