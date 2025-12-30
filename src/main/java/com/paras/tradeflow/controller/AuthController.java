package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.*;
import com.paras.tradeflow.entity.RefreshToken;
import com.paras.tradeflow.security.jwt.JwtService;
import com.paras.tradeflow.service.RefreshTokenService;
import com.paras.tradeflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody UserLoginRequest request){

        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){

        String refreshToken = refreshTokenRequest.getRefreshToken();
        RefreshToken rt = refreshTokenService.verify(refreshToken);
        String newAccessToken = jwtService.generateToken(rt.getUser().getEmail());

        return ResponseEntity.ok(new AuthResponse(newAccessToken,refreshToken));
    }


}
