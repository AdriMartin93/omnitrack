package com.omnitrak.users.domain.models;

import com.omnitrak.users.domain.models.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserSettings {
    private final UUID userId;
    private Theme theme;
    private boolean profilePrivate;
    private boolean listPrivate;

    public static UserSettings createDefault(UUID userId){
        return new UserSettings(
                userId,
                Theme.LIGHT,
                false,
                false
        );
    }

    public void updateProfileVisibility(boolean profilePrivate) {
        this.profilePrivate = profilePrivate;
    }

    public void updateListVisibility(boolean listPrivate) {
        this.listPrivate = listPrivate;
    }

    public void changeTheme(Theme newTheme) {
        if (newTheme == null) {
            throw new IllegalArgumentException("El tema no puede ser nulo");
        }
        this.theme = newTheme;
    }
}
