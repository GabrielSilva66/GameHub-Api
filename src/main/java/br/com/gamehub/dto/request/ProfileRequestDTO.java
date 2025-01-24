package br.com.gamehub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Represents the request for the profile details of a user.
 *
 * @param name the name of the user. This field must not exceed 50 characters.
 * @param birthDate the birthdate of the user, formatted as "yyyy-MM-dd".
 * @param gender the gender of the user.
 */
public record ProfileRequestDTO(
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        String name,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        String gender) {
}
