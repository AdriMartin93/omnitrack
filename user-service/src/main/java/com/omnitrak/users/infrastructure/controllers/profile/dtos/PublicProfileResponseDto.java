package com.omnitrak.users.infrastructure.controllers.profile.dtos;

import com.omnitrak.users.domain.models.PublicProfile;

import java.util.UUID;

public record PublicProfileResponseDto(
        UUID userId,
        String username,
        String bio,
        String avatarUrl,
        String bannerUrl) {

    public static PublicProfileResponseDto fromDomain(PublicProfile profile) {
        return new PublicProfileResponseDto(
                profile.getId(),
                profile.getUsername(),
                profile.getBio(),
                profile.getAvatarUrl(),
                profile.getBannerUrl()
        );
    }
}
