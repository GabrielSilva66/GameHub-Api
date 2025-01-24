/**
 * Utility class for mapping between {@link AssessmentRequestDTO}, {@link Assessment} entity, and {@link AssessmentResponseDTO}.
 *
 * <p>
 * This class provides methods to convert an {@link AssessmentRequestDTO} into an {@link Assessment} entity and vice versa. It also maps an {@link Assessment} entity to a {@link AssessmentResponseDTO} for returning responses.
 * </p>
 *
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.AssessmentRequestDTO;
import br.com.gamehub.dto.response.AssessmentResponseDTO;
import br.com.gamehub.model.Assessment;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.User;


public class AssessmentMapper {

    /**
     * Converts an {@link AssessmentRequestDTO} to an {@link Assessment} entity.
     *
     * @param assessmentDTO The DTO to be converted.
     * @param user The {@link User} associated with the assessment.
     * @param game The {@link Game} associated with the assessment.
     * @return The corresponding {@link Assessment} entity.
     */
    public static Assessment toEntity(AssessmentRequestDTO assessmentDTO, User user, Game game) {
        Assessment assessment = new Assessment();
        assessment.setGame(game);
        assessment.setUser(user);
        assessment.setRating(assessmentDTO.rating());
        assessment.setComment(assessmentDTO.comment());

        return  assessment;
    }

    /**
     * Converts an {@link Assessment} entity to an {@link AssessmentResponseDTO}.
     *
     * @param assessment The {@link Assessment} entity to be converted.
     * @return The corresponding {@link AssessmentResponseDTO}.
     */
    public static AssessmentResponseDTO toResponse(Assessment assessment) {
        return new AssessmentResponseDTO(
                assessment.getId(),
                assessment.getRating().doubleValue(),
                assessment.getComment());
    }
}

