package com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;

@Slf4j
@Service
public class TwitchAuthService {

    @Value("${external.igdb.client-id}")  //esta configurado en el yml del config server
    private String clientId;

    @Value("${external.igdb.client-secret}")
    private String clientSecret;

    private final RestClient authRestClient; //para llamar al servidor de autenticacion de twitcg

    private String cachedToken; //guardo el token para no pedirlo todo el rato

    private Instant tokenExpiration = Instant.MIN;

    public TwitchAuthService() {
        this.authRestClient = RestClient.builder()
                .baseUrl("https://id.twitch.tv")
                .build();
    }

    //syncronized evita condiciones de carrera, si hay muchas peticiones y el token caduca, solo un thread lo pide
    public synchronized String getAccessToken() {
        if(cachedToken != null && Instant.now().plusSeconds(60).isBefore(tokenExpiration)) {
            return cachedToken;
        }

        try{
            TwitchTokenResponse response = authRestClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth2/token")
                            .queryParam("client_id", clientId)
                            .queryParam("client_secret", clientSecret)
                            .queryParam("grant_type", "client_credentials")
                            .build())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    // Deserializamos el cuerpo de respuesta en nuestro DTO interno
                    .body(TwitchTokenResponse.class);

            if (response != null && response.getAccessToken() != null) {
                this.cachedToken = response.getAccessToken();
                this.tokenExpiration = Instant.now().plusSeconds(response.getExpiresIn());
                return cachedToken;
            }
        } catch (Exception e) {

            log.error("Error al obtener token de Twitch OAuth2 para IGDB: {}", e.getMessage());
        }

        return ""; // con esto evito el null pointer exception
    }

    //getter para que el cliente http de igdb pueda leer el client id
    public String getClientId() {
        return clientId;
    }

    @Data
    public static class TwitchTokenResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private Long expiresIn;

        @JsonProperty("token_type")
        private String tokenType;
    }


}
