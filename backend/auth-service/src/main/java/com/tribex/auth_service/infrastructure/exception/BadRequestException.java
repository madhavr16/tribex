package com.tribex.auth_service.infrastructure.exception;

/*
    Custom bad request exception
 */
public class BadRequestException
        extends RuntimeException {

    public BadRequestException(String message) {

        super(message);
    }
}