package com.example.keyclock.controller;


import com.example.keyclock.dto.LogoutResponseDto;
import com.example.keyclock.dto.TokenResponse;
import com.example.keyclock.dto.TokenResponseDto;
import com.example.keyclock.service.Interface.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/keycloak")
@CrossOrigin
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public Mono<TokenResponseDto> getToken(@RequestParam String username, @RequestParam String password) {
        return authService.getToken(username, password);
    }

    @PostMapping("/logout")
    public Mono<LogoutResponseDto> logout(@RequestParam String refreshToken) {
        return authService.logout(refreshToken);
    }

    @PostMapping("/refresh-token")
    public Mono<TokenResponse> refreshToken(@RequestParam String refreshToken) {
        return authService.getrefreshToken(refreshToken);
    }



}

