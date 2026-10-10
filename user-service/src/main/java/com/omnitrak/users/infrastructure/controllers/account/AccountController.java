package com.omnitrak.users.infrastructure.controllers.account;


import com.omnitrak.users.domain.ports.in.account.ChangeEmailUseCase;
import com.omnitrak.users.domain.ports.in.account.ChangePasswordUseCase;
import com.omnitrak.users.domain.ports.in.account.DeleteAccountUseCase;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;
import com.omnitrak.users.infrastructure.controllers.account.dtos.ChangeEmailRequestDto;
import com.omnitrak.users.infrastructure.controllers.account.dtos.ChangePasswordRequestDto;
import com.omnitrak.users.infrastructure.controllers.account.dtos.DeleteAccountRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class  AccountController {

    private final ChangeEmailUseCase changeEmailuseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final TokenProviderPort tokenProviderPort;

    public AccountController(
            ChangeEmailUseCase changeEmailUseCase,
            ChangePasswordUseCase changePasswordUseCase,
            DeleteAccountUseCase deleteAccountUseCase,
            TokenProviderPort tokenProviderPort) {
        this.changeEmailuseCase = changeEmailUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
        this.tokenProviderPort = tokenProviderPort;
    }

    @PatchMapping("/email")
    public ResponseEntity<Map<String, String>> changeEmail(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ChangeEmailRequestDto request) {

        UUID userId = extractUserId(authHeader);
        changeEmailuseCase.changeEmail(userId, request.newEmail(), request.password());

        return ResponseEntity.ok(Map.of("message", "Correo electrónico actualizado correctamente."));
    }

    @PatchMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ChangePasswordRequestDto request){

        UUID userId = extractUserId(authHeader);
        changePasswordUseCase.changePassword(userId, request.newPassword(), request.password());

        return ResponseEntity.ok(Map.of("message", "Contraseña modificada correctamente."));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAccount(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody DeleteAccountRequestDto request) {

        UUID userId = extractUserId(authHeader);
        deleteAccountUseCase.deleteAccount(userId, request.password());

        return ResponseEntity.ok(Map.of("message", "Cuenta eliminada satisfactoriamente."));
    }


    private UUID extractUserId(String authHeader) {
        String token = authHeader.replace("bearer ", "").trim();
        return tokenProviderPort.extractUserId(token);
    }

}
