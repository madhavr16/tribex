package com.tribex.auth_service.infrastructure.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/*
    Standard API error response
 */
@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}