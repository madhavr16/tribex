package com.tribex.auth_service.application.service;

import com.tribex.auth_service.application.dto.*;
import com.tribex.auth_service.domain.model.Role;
import com.tribex.auth_service.domain.model.User;
import com.tribex.auth_service.domain.repository.UserRepository;
import com.tribex.auth_service.infrastructure.exception.BadRequestException;
import com.tribex.auth_service.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
    Business logic for authentication
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    /*
        Register new user
     */
    public AuthResponse register(
            RegisterRequest request
    ) {

        /*
            Prevent duplicate emails
         */
        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new BadRequestException(
                    "Email already exists"
            );
        }

        /*      
            Create user object
         */
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())

                /*
                    Encrypt password
                 */
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                /*
                    Set user role
                 */
                .role(
                        Role.USER
                )
                .build();

        /*
            Save user in DB
         */
        userRepository.save(user);

        /*
            Generate JWT token
         */
        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new AuthResponse(token);
    }

    /*
        Login user
     */
    public AuthResponse login(
            LoginRequest request
    ) {

        /*
            Find user by email
         */
        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(() ->
                new BadRequestException(
                        "Invalid credentials"
                )
        );

        /*
            Check password
         */
        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new BadRequestException(
                    "Invalid credentials"
            );
        }

        /*
            Generate token
         */
        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new AuthResponse(token);
    }
}