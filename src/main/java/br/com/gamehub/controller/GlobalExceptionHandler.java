package br.com.gamehub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle generic exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Exception: {}", errorId, ex.getMessage(), ex);

        return buildErrorResponse(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact support with the error ID.");
    }

    /**
     * Handle validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Validation failed: {}", errorId, ex.getMessage(), ex);

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ResponseEntity<Map<String, Object>> response = buildErrorResponse(
                errorId, HttpStatus.BAD_REQUEST, "Validation failed. Please check the provided information.");
        response.getBody().put("fieldErrors", fieldErrors);
        return response;
    }

    /**
     * Handle entity not found exceptions
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Entity not found: {}", errorId, ex.getMessage(), ex);

        return buildErrorResponse(errorId, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Utility method to build error response
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String errorId, HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorId", errorId);
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(errorResponse);
    }
}
