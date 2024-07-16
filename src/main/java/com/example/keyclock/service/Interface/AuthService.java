package com.example.keyclock.service.Interface;

import com.example.keyclock.dto.LogoutResponseDto;
import com.example.keyclock.dto.TokenResponse;
import com.example.keyclock.dto.TokenResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<TokenResponseDto> getToken(String username, String password);
    Mono<LogoutResponseDto> logout(String refreshToken);

    Mono<TokenResponse> getrefreshToken(@RequestParam String refreshToken);
}
