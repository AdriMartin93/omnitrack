package com.omnitrak.users.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String avatarUrl;
    private String bannerUrl;
    private String bio;
    private boolean active;
    private LocalDateTime lastLoginDate;
    private LocalDateTime creationDate;


    public void activate(){

        this.active=true;
    }

    public void changeEmail(String newEmail){
        if(newEmail == null || !newEmail.contains("@")){
            throw new IllegalArgumentException("Dirección de correo invalida");
        }
        this.email = newEmail;
    }

    public void changePassword(String newPassword){
        if(newPassword == null || newPassword.isBlank()){
            throw new IllegalArgumentException("Password invalida");
        }
        this.password = newPassword;
    }

    public void deleteAccount(){
        this.active=false;
        this.email = "deleted_" + this.email;
        this.username = "deleted_" + this.username;
        this.password = "deleted_" + this.password;
    }

    public void updateAvatar(String newAvatarUrl){
        if(newAvatarUrl != null && newAvatarUrl.isBlank()){
            throw new IllegalArgumentException("Avatar de usuario invalido");
        }
        this.avatarUrl = newAvatarUrl;
    }

    public void updateBanner(String newBannerUrl){
        if(newBannerUrl != null && newBannerUrl.isBlank()){
            throw new IllegalArgumentException("Banner de usuario invalido");
        }
        this.bannerUrl = newBannerUrl;
    }

    public void updateBio(String newBio){
        if(newBio != null && newBio.length()> 500) {
            throw new IllegalArgumentException("La biografía no puede exceder los 500 caracteres");
        }
        this.bio = newBio;
    }

    public void updateUsername(String newUsername){
        if(newUsername == null || newUsername.isBlank()){
            throw new IllegalArgumentException("Nombre de usuario invalido");
        }
        this.username = newUsername;
    }





}