package com.omnitrak.users.infrastructure.repositories;

import com.omnitrak.users.infrastructure.entities.UserSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserSettingsRepository extends JpaRepository<UserSettingsEntity, UUID> {
}
