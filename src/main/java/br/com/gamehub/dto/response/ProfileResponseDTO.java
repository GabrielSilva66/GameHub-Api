package br.com.gamehub.dto.response;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the response for a user profile.
 * This DTO contains the profile details such as the user's ID, name, birthdate, and gender.
 *
 * @param id the unique identifier of the user's profile.
 * @param name the name of the user.
 * @param birthDate the birthdate of the user.
 * @param gender the gender of the user.
 */
public record ProfileResponseDTO(
        Long id,
        String name,
        LocalDate birthDate,
        String gender

) {}
