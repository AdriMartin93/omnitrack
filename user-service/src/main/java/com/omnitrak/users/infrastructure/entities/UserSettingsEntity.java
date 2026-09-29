package com.omnitrak.users.infrastructure.entities;


import com.omnitrak.users.domain.models.enums.Theme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsEntity {

    @Id
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_settings_user_id"))
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'LIGHT'")
    private Theme theme = Theme.LIGHT;

    @Column(name = "profile_private", nullable = false)
    private boolean profilePrivate = false;

    @Column(name = "list_private", nullable = false)
    private boolean listPrivate;
}
