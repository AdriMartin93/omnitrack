package com.omnitrak.users.infrastructure.mappers;

import com.omnitrak.users.domain.models.UserSettings;
import com.omnitrak.users.domain.models.enums.Theme;
import com.omnitrak.users.infrastructure.entities.UserSettingsEntity;

public class UserSettingsEntityMapper {

    public UserSettings toDomain(UserSettingsEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserSettings(
                entity.getUserId(),
                entity.getTheme() != null ? entity.getTheme() : Theme.LIGHT,
                entity.isProfilePrivate(),
                entity.isListPrivate()
        );
    }

    public UserSettingsEntity toEntity(UserSettings domain) {
        if (domain == null) {
            return null;
        }
        UserSettingsEntity entity = new UserSettingsEntity();
        entity.setUserId(domain.getUserId());
        entity.setTheme(domain.getTheme() != null ? domain.getTheme() : Theme.LIGHT);
        entity.setProfilePrivate(domain.isProfilePrivate());
        entity.setListPrivate(domain.isListPrivate());
        return entity;
    }
}
