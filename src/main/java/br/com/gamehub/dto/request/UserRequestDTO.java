package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) representing the request for creating or updating a user.
 * This DTO holds the user's email, password hash, and user type.
 *
 * @param email the user's email. It must not be blank and must follow a valid email format.
 * @param passwordHash the hashed password of the user. It must not be blank.
 * @param userType the type of user (e.g., "admin", "user"). It can be null.
 */
public record UserRequestDTO(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is mandatory")
        String passwordHash,

        String userType


) {}
