package com.omnitrak.users.domain.factory;

import com.omnitrak.users.domain.models.PublicProfile;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.models.UserSettings;

public class PublicProfileFactory {

    public static PublicProfile create(User user, UserSettings settings){
        if(user == null){
            return null;
        }

        boolean isPrivate = (settings != null) && settings.isProfilePrivate();

        return PublicProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .bannerUrl(user.getBannerUrl())
                .bio(user.getBio())
                .creationDate(user.getCreationDate())
                .isProfilePrivate(isPrivate)
                .build();
    }
}
