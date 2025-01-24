/**
 * GlobalExceptionHandler
 * 
 * <p>
 * Global exception handler for the application.
 * </p>
 * 
 * @since 2025-01-23
 */

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
     * Handles generic exceptions that occur in the application.
     *
     * <p>
     * This method captures any exception that is not specifically handled by other
     * exception handlers. It logs the error with a unique error ID and generates an
     * error response with a 500 (Internal Server Error) status. The error response
     * includes a message instructing users to contact support with the provided
     * error ID.
     *
     * @param ex The exception that occurred.
     * @return A ResponseEntity containing a map with error details, including a
     *         unique error ID.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Exception: {}", errorId, ex.getMessage(), ex);

        return buildErrorResponse(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact support with the error ID.");
    }

    /**
     * Handles validation exceptions that occur when method arguments are not valid.
     *
     * <p>
     * This method captures instances of {@link MethodArgumentNotValidException} and
     * logs the validation error with a unique error ID. It constructs an error
     * response
     * with a 400 (Bad Request) status, including details about the specific field
     * errors
     * that caused the validation failure.
     *
     * @param ex The exception containing details of the validation failure.
     * @return A ResponseEntity containing a map with error details, including a
     *         unique
     *         error ID and the list of field errors.
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
     * Handles exceptions that occur when an entity cannot be found.
     *
     * <p>
     * This method captures instances of {@link EntityNotFoundException} and logs
     * the
     * exception with a unique error ID. It constructs an error response with a 404
     * (Not
     * Found) status, including the error message and the error ID.
     *
     * @param ex The exception containing details of the entity not found.
     * @return A ResponseEntity containing a map with error details, including a
     *         unique
     *         error ID and the error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID: {} - Entity not found: {}", errorId, ex.getMessage(), ex);

        return buildErrorResponse(errorId, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Constructs an error response with a unique error ID, HTTP status, error
     * message, and timestamp.
     *
     * @param errorId Unique error ID.
     * @param status  HTTP status code.
     * @param message Error message.
     * @return A ResponseEntity containing a map with error details, including a
     *         unique error ID and the error message.
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
