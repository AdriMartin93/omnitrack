package com.omnitrak.users.infrastructure.adapters.persistence;

import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.ports.out.settings.SettingsPersistencePort;
import com.omnitrak.users.infrastructure.entities.UserEntity;
import com.omnitrak.users.infrastructure.entities.UserSettingsEntity;
import com.omnitrak.users.infrastructure.mappers.UserSettingsEntityMapper;
import com.omnitrak.users.infrastructure.repositories.JpaUserRepository;
import com.omnitrak.users.infrastructure.repositories.JpaUserSettingsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class SettingsPersistenceAdapter implements SettingsPersistencePort {

    private final JpaUserSettingsRepository settingsRepository;
    private final JpaUserRepository userRepository;
    private final UserSettingsEntityMapper settingsMapper;

    public SettingsPersistenceAdapter(
            JpaUserSettingsRepository settingsRepository,
            JpaUserRepository userRepository,
            UserSettingsEntityMapper settingsMapper){
        this.settingsRepository = settingsRepository;
        this.userRepository = userRepository;
        this.settingsMapper = settingsMapper;
    }

    @Override
    public Optional<UserSettings> findByUserId(UUID userId){
        return settingsRepository.findById(userId)
                .map(settingsMapper::toDomain);
    }

    @Override
    public UserSettings save(UserSettings settings){
        UserSettingsEntity entity = settingsMapper.toEntity(settings);

        UserEntity userReference = userRepository.getReferenceById(settings.getUserId());
        entity.setUser(userReference);

        UserSettingsEntity saved = settingsRepository.save(entity);
        return settingsMapper.toDomain(saved);
    }
}
