package br.com.gamehub.dto.response;

/**
 * Data Transfer Object (DTO) representing the response for an assessment of a game.
 * This DTO contains the assessment ID, rating, and the comment provided by the user.
 *
 * @param idAssessment the unique identifier of the assessment.
 * @param rating the rating score given to the game (e.g., between 1 and 10).
 * @param comment the feedback comment provided by the user for the game.
 */
public record AssessmentResponseDTO(
    Long idAssessment,
    Double rating,
    String comment
) {
}
