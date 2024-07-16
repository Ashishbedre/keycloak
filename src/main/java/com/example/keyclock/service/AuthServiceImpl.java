package com.example.keyclock.service;

import com.example.keyclock.dto.LogoutResponseDto;
import com.example.keyclock.dto.TokenResponse;
import com.example.keyclock.dto.TokenResponseDto;
import com.example.keyclock.service.Interface.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.scope}")
    private String scope;


    @Override
    public Mono<TokenResponseDto> getToken(String username, String password) {
        String tokenEndpoint = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(tokenEndpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", grantType)
                        .with("client_id", clientId)
                        .with("username", username)
                        .with("password", password)
                        .with("scope", scope))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    TokenResponseDto tokenResponseDto = new TokenResponseDto();
                    tokenResponseDto.setAccessToken((String) response.get("access_token"));
                    tokenResponseDto.setRefreshToken((String) response.get("refresh_token"));
                    return tokenResponseDto;
                });
    }

    @Override
    public Mono<LogoutResponseDto> logout(String refreshToken) {
        String logoutEndpoint = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/logout";
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(logoutEndpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("refresh_token", refreshToken))
                .retrieve()
                .bodyToMono(Void.class)
                .map(response -> {
                    LogoutResponseDto logoutResponseDto = new LogoutResponseDto();
                    logoutResponseDto.setMessage("Successfully logged out");
                    return logoutResponseDto;
                })
                .onErrorResume(e -> {
                    LogoutResponseDto errorResponse = new LogoutResponseDto();
                    errorResponse.setMessage("Error: " + e.getMessage());
                    return Mono.just(errorResponse);
                });
    }

    @Override
    public Mono<TokenResponse> getrefreshToken(String refreshToken) {
        String tokenEndpoint = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(tokenEndpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                        .with("client_id", clientId)
                        .with("refresh_token", refreshToken))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> new TokenResponse((String) response.get("access_token"), (String) response.get("refresh_token")))
                .onErrorResume(e -> Mono.just(new TokenResponse("Error: " + e.getMessage(), null)));
    }
}
