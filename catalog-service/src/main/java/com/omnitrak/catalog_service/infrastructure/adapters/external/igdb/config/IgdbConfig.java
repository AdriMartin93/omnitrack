package com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.config;


import com.omnitrak.catalog_service.infrastructure.adapters.external.igdb.auth.TwitchAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class IgdbConfig {

    private final TwitchAuthService twitchAuthService;


    @Value("${external.igdb.base-url:https://api.igdb.com/v4}")
    private String baseUrl;

    @Bean
    public RestClient igdbRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInitializer(request -> {
                    request.getHeaders().set("Client-ID", twitchAuthService.getClientId());
                    request.getHeaders().setBearerAuth(twitchAuthService.getAccessToken());
                })
                .build();
    }
}
