package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) representing the request for creating or updating an assessment.
 * This DTO holds the rating and comment for a game assessment.
 *
 * @param rating the rating for the game assessment. The value must be between 1 and 10.
 * @param comment the comment for the game assessment. It must not exceed 1024 characters.
 */
public record AssessmentRequestDTO(
        @Min(value = 1, message = "The evaluation score must be between 1 and 10.")
        @Max(value = 10,  message = "The evaluation score must be between 1 and 10.")
        Integer rating,

        @Size(max = 1024, message = "Comment must not exceed 1024 characters")
        String comment
) {}
