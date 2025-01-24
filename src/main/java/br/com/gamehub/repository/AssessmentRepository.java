/**
 * AssessmentRepository
 *
 * <p>
 * Repository interface for handling data access for the Assessment entity.
 * Provides methods for searching and querying game assessments from the database.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    /**
     * Finds an assessment by the user and game IDs.
     *
     * <p>
     * This method retrieves a user's assessment for a specific game, if it exists.
     * </p>
     *
     * @param userId the ID of the user who made the assessment.
     * @param gameId the ID of the game being assessed.
     * @return an {@link Optional} containing the found {@link Assessment}, or an empty {@link Optional} if no assessment is found.
     */
    Optional<Assessment> findByUserIdAndGameId(Long userId, Long gameId);

    /**
     * Finds all assessments for a specific user.
     *
     * <p>
     * This method retrieves all assessments made by a user across all games.
     * </p>
     *
     * @param userId the ID of the user whose assessments are being retrieved.
     * @return a list of {@link Assessment} entities associated with the given user.
     */
    List<Assessment> findAllByUserId(Long userId);

    /**
     * Finds all assessments for a specific game.
     *
     * <p>
     * This method retrieves all assessments made for a particular game by different users.
     * </p>
     *
     * @param gameId the ID of the game whose assessments are being retrieved.
     * @return a list of {@link Assessment} entities associated with the given game.
     */
    List<Assessment> findAllByGameId(Long gameId);
}
