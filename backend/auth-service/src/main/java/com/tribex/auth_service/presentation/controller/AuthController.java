package com.tribex.auth_service.presentation.controller;

import com.tribex.auth_service.application.dto.*;
import com.tribex.auth_service.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
    Handles auth APIs
 */
@RestController

@RequestMapping("/api/auth")

@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
        Register endpoint
     */
    @PostMapping("/register")
    public AuthResponse register(

            @Valid

            @RequestBody
            RegisterRequest request
    ) {

        return authService.register(request);
    }

    /*
        Login endpoint
     */
    @PostMapping("/login")
    public AuthResponse login(

            @RequestBody
            LoginRequest request
    ) {

        return authService.login(request);
    }
}