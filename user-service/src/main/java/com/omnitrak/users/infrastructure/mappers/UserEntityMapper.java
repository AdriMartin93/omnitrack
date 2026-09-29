package com.omnitrak.users.infrastructure.mappers;

import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity){
        if(entity == null){
            return null;
        }
        return new User(
             entity.getId(),
             entity.getUsername(),
             entity.getPassword(),
             entity.getEmail(),
             entity.getRole(),
             entity.getAvatarUrl(),
             entity.getBannerUrl(),
             entity.getBio(),
             entity.isActive(),
             entity.getLastLoginDate(),
             entity.getCreationDate()
        );
    }


    public UserEntity toEntity(User domain){
        if(domain == null){
            return null;
        }
        return new UserEntity(
                domain.getId(),
                domain.getUsername(),
                domain.getPassword(),
                domain.getEmail(),
                domain.getRole(),
                domain.getAvatarUrl(),
                domain.getBannerUrl(),
                domain.getBio(),
                domain.isActive(),
                domain.getLastLoginDate(),
                domain.getCreationDate()
        );
    }
}
