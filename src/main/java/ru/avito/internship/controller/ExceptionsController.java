package ru.avito.internship.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.avito.internship.domain.dto.ExceptionResponse;
import ru.avito.internship.domain.exception.InsufficientFundsException;
import ru.avito.internship.domain.exception.MerchNotFoundException;
import ru.avito.internship.domain.exception.UserNotFoundException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(@NotNull AuthenticationException e) {
        return ResponseEntity.status(UNAUTHORIZED)
                .body(new ExceptionResponse("Authentication failed: " + e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(@NotNull UserNotFoundException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(MerchNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMerchNotFoundException(@NotNull MerchNotFoundException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ExceptionResponse> handleInsufficientFundsException(@NotNull InsufficientFundsException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(@NotNull RuntimeException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(e.getMessage()));
    }
}
