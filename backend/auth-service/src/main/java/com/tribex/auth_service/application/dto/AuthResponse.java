package com.tribex.auth_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    Response after successful login/register
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
}
