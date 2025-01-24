/**
 * UserObtainedGameRepository
 *
 * <p>
 * Repository interface for handling data access for the UserObtainedGame entity.
 * Provides methods for searching and querying games obtained by users in the system.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.UserObtainedGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserObtainedGameRepository extends JpaRepository<UserObtainedGame, Long> {

    /**
     * Finds a UserObtainedGame by the user and game IDs.
     *
     * <p>
     * This method retrieves the specific game that a user has obtained, based on the user and game identifiers.
     * </p>
     *
     * @param userId the ID of the user who obtained the game.
     * @param gameId the ID of the game that is being queried.
     * @return an {@link Optional} containing the found {@link UserObtainedGame}, or an empty {@link Optional} if no game is found.
     */
    Optional<UserObtainedGame> findByUserIdAndGameId(Long userId, Long gameId);

    /**
     * Finds all games obtained by a specific user.
     *
     * <p>
     * This method retrieves all games that the user has obtained.
     * </p>
     *
     * @param userId the ID of the user whose obtained games are being retrieved.
     * @return a list of {@link UserObtainedGame} entities associated with the given user.
     */
    List<UserObtainedGame> findAllByUserId(Long userId);

    /**
     * Checks if a user has obtained a specific game.
     *
     * <p>
     * This method checks whether the user has obtained the specified game by the given user and game identifiers.
     * </p>
     *
     * @param userId the ID of the user to check.
     * @param gameId the ID of the game to check.
     * @return {@code true} if the user has obtained the game, {@code false} otherwise.
     */
    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}
