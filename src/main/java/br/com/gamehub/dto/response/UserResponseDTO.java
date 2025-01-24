package br.com.gamehub.dto.response;

import br.com.gamehub.enums.UserType;

/**
 * Data Transfer Object (DTO) representing the response for a user.
 * This DTO contains the user's details including their ID, email, and user type.
 *
 * @param id the unique identifier of the user.
 * @param email the email address of the user.
 * @param userType the type of the user (e.g., ADMIN, COMMON).
 */
public record UserResponseDTO(
        Long id,
        String email,
        UserType userType
) {}
