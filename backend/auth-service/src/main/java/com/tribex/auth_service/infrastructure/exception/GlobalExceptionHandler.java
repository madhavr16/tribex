package com.tribex.auth_service.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/*
    Handles exceptions globally
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
        Handle custom bad request exceptions
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(

            BadRequestException ex,

            HttpServletRequest request
    ) {

        return ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.BAD_REQUEST.value())

                .error(HttpStatus.BAD_REQUEST
                        .getReasonPhrase())

                .message(ex.getMessage())

                .path(request.getRequestURI())

                .build();
    }

    /*
        Handle validation errors
     */
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )

    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public ErrorResponse handleValidationException(

            MethodArgumentNotValidException ex,

            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()

                .getFieldError()

                .getDefaultMessage();

        return ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.BAD_REQUEST.value())

                .error("Validation Error")

                .message(message)

                .path(request.getRequestURI())

                .build();
    }

    /*
        Handle generic exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(

            Exception ex,

            HttpServletRequest request
    ) {

        return ErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.INTERNAL_SERVER_ERROR
                        .value())

                .error("Internal Server Error")

                .message(ex.getMessage())

                .path(request.getRequestURI())

                .build();
    }
}