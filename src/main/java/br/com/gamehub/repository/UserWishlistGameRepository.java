/**
 * UserWishlistGameRepository
 *
 * <p>
 * Repository interface for handling data access for the UserWishlistGame entity.
 * Provides methods for querying user wishlist games from the database.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.UserWishlistGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWishlistGameRepository extends JpaRepository<UserWishlistGame, Long> {

    /**
     * Finds a user’s wishlist game by their user ID and the game ID.
     *
     * <p>
     * This method retrieves a specific game from the wishlist of a user using
     * their user ID and the game ID.
     * </p>
     *
     * @param userId the ID of the user whose wishlist game is being searched.
     * @param gameId the ID of the game in the wishlist to search for.
     * @return an {@link Optional} containing the found {@link UserWishlistGame}, or an empty {@link Optional} if no match is found.
     */
    Optional<UserWishlistGame> findByUserIdAndGameId(Long userId, Long gameId);

    /**
     * Finds all the wishlist games of a specific user by their user ID.
     *
     * <p>
     * This method retrieves all games in the wishlist for a user.
     * </p>
     *
     * @param userId the ID of the user whose wishlist games are being retrieved.
     * @return a {@link List} of {@link UserWishlistGame} objects, or an empty list if no games are found.
     */
    List<UserWishlistGame> findAllByUserId(Long userId);
}
