package com.tribex.auth_service.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

/*
    Spring Security configuration
 */
@Configuration
public class SecurityConfig {

    /*
        Password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
        Configure security rules
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                    Disable CSRF for REST APIs
                 */
                .csrf(csrf -> csrf.disable())

                /*
                    Configure endpoint authorization
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                            Allow auth APIs publicly
                         */
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        /*
                            Any other API requires auth
                         */
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}