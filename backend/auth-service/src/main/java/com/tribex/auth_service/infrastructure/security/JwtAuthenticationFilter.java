package com.tribex.auth_service.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
    JWT authentication filter

    Runs once for every request
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        /*
            Get Authorization header
         */
        final String authHeader =
                request.getHeader("Authorization");

        /*
            If header missing OR not Bearer token
         */
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        /*
            Extract token
         */
        String token =
                authHeader.substring(7);

        /*
            Extract email from JWT
         */
        String email =
                jwtService.extractEmail(token);

        /*
            Authenticate only if not already authenticated
         */
        if (email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            /*
                Load user from DB
             */
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);

            /*
                Create authentication token
             */
            UsernamePasswordAuthenticationToken
                    authToken =
                    new UsernamePasswordAuthenticationToken(

                            userDetails,

                            null,

                            userDetails.getAuthorities()
                    );

            /*
                Attach request details
             */
            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            /*
                Set authenticated user
             */
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }

        /*
            Continue request
         */
        filterChain.doFilter(request, response);
    }
}