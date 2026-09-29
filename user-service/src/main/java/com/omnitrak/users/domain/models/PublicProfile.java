package com.omnitrak.users.domain.models;



import java.time.LocalDateTime;
import java.util.UUID;

public class PublicProfile {

    private final UUID id;
    private final String username;
    private final String avatarUrl;
    private final String bannerUrl;
    private final String bio;
    private final LocalDateTime creationDate;
    private final boolean isProfilePrivate;


    private PublicProfile(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.avatarUrl = builder.avatarUrl;
        this.bannerUrl = builder.bannerUrl;
        this.bio = builder.bio;
        this.creationDate = builder.creationDate;
        this.isProfilePrivate = builder.isProfilePrivate;
    }


    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getBio() {
        return bio;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isProfilePrivate() {
        return isProfilePrivate;
    }


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private UUID id;
        private String username;
        private String avatarUrl;
        private String bannerUrl;
        private String bio;
        private LocalDateTime creationDate;
        private boolean isProfilePrivate;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder bannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder isProfilePrivate(boolean isProfilePrivate) {
            this.isProfilePrivate = isProfilePrivate;
            return this;
        }

        public PublicProfile build() {
            return new PublicProfile(this);
        }
    }
}