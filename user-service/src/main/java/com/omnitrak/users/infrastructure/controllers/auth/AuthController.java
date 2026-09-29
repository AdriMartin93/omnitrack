package com.omnitrak.users.infrastructure.controllers.auth;


import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.in.auth.*;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;
import com.omnitrak.users.infrastructure.controllers.auth.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ActivateAccountUseCase activateAccountUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final RequestPasswordUseCase requestPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final TokenProviderPort tokenProviderPort;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            ActivateAccountUseCase activateAccountUseCase,
            AuthenticateUserUseCase authenticateUserUseCase,
            RefreshTokenUseCase refreshTokenUseCase,
            LogoutUserUseCase logoutUserUseCase,
            RequestPasswordUseCase requestPasswordUseCase,
            ResetPasswordUseCase resetPasswordUseCase,
            TokenProviderPort tokenProviderPort
    ){
        this.registerUserUseCase = registerUserUseCase;
        this.activateAccountUseCase = activateAccountUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.requestPasswordUseCase = requestPasswordUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.tokenProviderPort = tokenProviderPort;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request){
        User registeredUser = registerUserUseCase.registerUser(
                request.username(),
                request.email(),
                request.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.fromDomain(registeredUser));
    }

    @GetMapping("/activate")
    public ResponseEntity<Map<String, String>> activateAccount(@RequestParam("token") String token) {
        activateAccountUseCase.activate(token);
        return ResponseEntity.ok(Map.of("message", "Cuenta activada con éxito. Ya puedes iniciar sesión."));
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthTokens tokens = authenticateUserUseCase.login(request.username(), request.password());
        return ResponseEntity.ok(AuthResponseDto.of(tokens.getAccessToken(), tokens.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestHeader("Authorization") String authorizationHeader) {
        String refresToken = authorizationHeader.replace("Bearer", "").trim();
        AuthTokens tokens = refreshTokenUseCase.refresh(refresToken);
        return ResponseEntity.ok(AuthResponseDto.of(tokens.getAccessToken(), tokens.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String , String>> logout(@RequestHeader("Authorization") String authorizationHeader,
                                                       @Valid @RequestBody LogoutRequestDto request) {
        String accessToken = authorizationHeader.replace("Bearer", "").trim();
        UUID userId = tokenProviderPort.extractUserId(accessToken);
        logoutUserUseCase.logout(userId, request.refreshToken());
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada correctamente."));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request) {
        requestPasswordUseCase.requestPasswordReset(request.email());
        return ResponseEntity.ok(Map.of("message", "Si el correo está registrado, se ha enviado un enlace de recuperación."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
        resetPasswordUseCase.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok(Map.of("message", "Contraseña restablecida con éxito. Ya puedes iniciar sesión con tu nueva clave."));
    }
}
