package com.omnitrak.users.infrastructure.controllers.profile;


import com.omnitrak.users.domain.models.PublicProfile;
import com.omnitrak.users.domain.ports.in.profile.*;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;
import com.omnitrak.users.infrastructure.controllers.profile.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final GetPublicProfileUseCase getPublicProfileUseCase;
    private final TokenProviderPort tokenProviderPort;
    private final UpdateUsernameUseCase updateUsernameUseCase;
    private final UpdateAvatarUseCase updateAvatarUseCase;
    private final UpdateBannerUseCase updateBannerUseCase;
    private final UpdateBioUseCase updateBioUseCase;


    public ProfileController(
            GetPublicProfileUseCase getPublicProfileUseCase,
            TokenProviderPort tokenProviderPort,
            UpdateUsernameUseCase updateUsernameUseCase,
            UpdateAvatarUseCase updateAvatarUseCase,
            UpdateBannerUseCase updateBannerUseCase,
            UpdateBioUseCase updateBioUseCase
    ){
        this.getPublicProfileUseCase = getPublicProfileUseCase;
        this.tokenProviderPort = tokenProviderPort;
        this.updateUsernameUseCase = updateUsernameUseCase;
        this.updateAvatarUseCase = updateAvatarUseCase;
        this.updateBannerUseCase = updateBannerUseCase;
        this.updateBioUseCase = updateBioUseCase;
    }


    @GetMapping("/{username}")
    public ResponseEntity<PublicProfileResponseDto> getByUsername(@PathVariable("username") String username) {
        PublicProfile profile = getPublicProfileUseCase.getByUsername(username);
        return ResponseEntity.ok(PublicProfileResponseDto.fromDomain(profile));
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<PublicProfileResponseDto> getById(@PathVariable("userId") UUID id) {
        PublicProfile profile = getPublicProfileUseCase.getById(id);
        return ResponseEntity.ok(PublicProfileResponseDto.fromDomain(profile));
    }

    @PatchMapping("/username")
    public ResponseEntity<Map<String, String>> updateUsername(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateUsernameRequestDto request){
        UUID userId = extractUserId(authHeader);
        updateUsernameUseCase.updateUsername(userId, request.newUsername());
        return ResponseEntity.ok(Map.of("message", "El nombre de usuario se ha actualizado correctamente."));
    }

    @PatchMapping("/avatar")
    public ResponseEntity<Map<String, String>> updateAvatar(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateAvatarRequestDto request){

        UUID userId = extractUserId(authHeader);
        updateAvatarUseCase.updateAvatar(userId, request.newAvatar());

        return ResponseEntity.ok(Map.of("message", "Avatar actualizado con éxito."));
    }

    @PatchMapping("/banner")
    public ResponseEntity<Map<String, String>> updateBanner(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateBannerRequestDto request){
        UUID userId = extractUserId(authHeader);
        updateBannerUseCase.updateBanner(userId, request.newBanner());

        return ResponseEntity.ok(Map.of("message", "Banner actualizado con éxito."));
    }

    @PatchMapping("/bio")
    public ResponseEntity<Map<String, String>> updateBio(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateBioRequestDto request){

        UUID userId = extractUserId(authHeader);
        updateBannerUseCase.updateBanner(userId, request.newBio());

        return ResponseEntity.ok(Map.of("message", "Biografía actualizada con exito."));
    }


    private UUID extractUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        return tokenProviderPort.extractUserId(token);
    }
}
