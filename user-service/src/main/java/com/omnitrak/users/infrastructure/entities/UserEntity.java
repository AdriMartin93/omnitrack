package com.omnitrak.users.infrastructure.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, updatable = true, length = 250)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "banner_url", length = 500)
    private String bannerUrl;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

}
