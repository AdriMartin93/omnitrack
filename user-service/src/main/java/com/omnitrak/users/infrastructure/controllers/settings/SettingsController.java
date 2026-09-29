package com.omnitrak.users.infrastructure.controllers.settings;


import com.omnitrak.users.domain.ports.in.settings.GetUserSettingsUseCase;
import com.omnitrak.users.domain.ports.in.settings.UpdatePrivacySettingsUseCase;
import com.omnitrak.users.domain.ports.in.settings.UpdateThemePreferenceUseCase;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;
import com.omnitrak.users.infrastructure.controllers.settings.dtos.UpdateListVisibilityRequestDto;
import com.omnitrak.users.infrastructure.controllers.settings.dtos.UpdateProfileVisibilityRequestDto;
import com.omnitrak.users.infrastructure.controllers.settings.dtos.UpdateThemeRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/settings")
public class SettingsController {

    private final GetUserSettingsUseCase getUserSettingsUseCase;
    private final UpdateThemePreferenceUseCase updateThemePreferenceUseCase;
    private final UpdatePrivacySettingsUseCase updatePrivacySettingsUseCase;
    private final TokenProviderPort tokenProviderPort;

    public SettingsController(
            GetUserSettingsUseCase getUserSettingsUseCase,
            UpdateThemePreferenceUseCase updateThemePreferenceUseCase,
            UpdatePrivacySettingsUseCase updatePrivacySettingsUseCase,
            TokenProviderPort tokenProviderPort
    ){
        this.getUserSettingsUseCase = getUserSettingsUseCase;
        this.updateThemePreferenceUseCase = updateThemePreferenceUseCase;
        this.updatePrivacySettingsUseCase = updatePrivacySettingsUseCase;
        this.tokenProviderPort = tokenProviderPort;
    }

    @GetMapping("/theme")
    public ResponseEntity<Map<String, String>> updateTheme(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateThemeRequestDto request
            ){
        UUID userId = extractUserId(authHeader);
        updateThemePreferenceUseCase.updateTheme(userId, request.theme());
        return ResponseEntity.ok(Map.of("message", "Tema actualizado con éxito"));
    }

    @PatchMapping("/privacy/profile")
    public ResponseEntity<Map<String, String>> updateProfileVisibility(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateProfileVisibilityRequestDto request
    ){
        UUID userId = extractUserId(authHeader);
        updatePrivacySettingsUseCase.updateProfileVisibility(userId, request.profileVisible());

        return ResponseEntity.ok(Map.of("message", "Privacidad actualizada"));
    }

    @PatchMapping("/privacy/list")
    public ResponseEntity<Map<String, String>> updateListVisibility(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateListVisibilityRequestDto request
    ){
        UUID userId = extractUserId(authHeader);
        updatePrivacySettingsUseCase.updateListVisibility(userId, request.isPrivateList());

        return ResponseEntity.ok(Map.of("message", "Visibilidad de tu lista actualizada"));
    }


    private UUID extractUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        return tokenProviderPort.extractUserId(token);
    }

}
