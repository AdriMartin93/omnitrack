package com.omnitrak.users.infrastructure.adapters.security;

import com.omnitrak.users.domain.models.AuthTokens;
import com.omnitrak.users.domain.models.User;
import com.omnitrak.users.domain.ports.out.auth.TokenProviderPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {

    private final SecretKey signingKey;
    private final long tokenValidityInMs;
    private final long refreshValidityInMs;


    public JwtTokenProviderAdapter(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}")long tokenValidityInMs,
            @Value("${jwt.refresh-expiration-ms}") long refreshValidityInMs) {

        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMs = tokenValidityInMs;
        this.refreshValidityInMs = refreshValidityInMs;
    }

    @Override
    public AuthTokens generateToken(User user){
        Map<String, Object> claims = Map.of(
                "userId", user.getId().toString(),
                "role", user.getRole()
        );

        String accesToken = createToken(user.getUsername(), claims, tokenValidityInMs);
        String refreshToken = createToken(user.getUsername(), Map.of("userId", user.getId().toString()), refreshValidityInMs);

        return new AuthTokens(accesToken, refreshToken, "Bearer", tokenValidityInMs / 1000);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public AuthTokens refreshToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh token inválido o expirado");
        }

        Claims claims = extractAllClaims(refreshToken);
        String username = claims.getSubject();
        String userId = claims.get("userId", String.class);

        Map<String, Object> newClaims = (userId != null)
                ? Map.of("userId", userId)
                : Collections.emptyMap();

        String newAccessToken = createToken(username, newClaims, tokenValidityInMs);
        String newRefreshToken = createToken(username, newClaims, refreshValidityInMs);

        return new AuthTokens(newAccessToken, newRefreshToken, "Bearer", tokenValidityInMs / 1000);
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        String userIdStr = claims.get("userId", String.class);
        if (userIdStr == null) {
            throw new IllegalArgumentException("El token no contiene el claim 'userId'");
        }
        return UUID.fromString(userIdStr);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createToken(String subject, Map<String, Object> extraClaims, long validityDurationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityDurationMs);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }
}
