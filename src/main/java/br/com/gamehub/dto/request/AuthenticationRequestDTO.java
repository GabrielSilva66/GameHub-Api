package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for authentication request.
 * This DTO contains the email and password required for a user to authenticate.
 *
 * @param email the email address of the user. It must be a valid email format and cannot be blank.
 * @param password the password of the user. It cannot be blank.
 */
public record AuthenticationRequestDTO(

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password) {
}
