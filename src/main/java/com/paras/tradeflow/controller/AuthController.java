package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.UserLoginRequest;
import com.paras.tradeflow.dto.UserRegisterRequest;
import com.paras.tradeflow.dto.UserResponse;
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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login (@RequestBody UserLoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }


}
