/**
 * Utility class for mapping between {@link UserWishlistGame}, {@link UserWishlistGameResponseDTO}, and their related entities.
 *
 * <p>
 * This class provides methods to convert a {@link User} and {@link Game} into a {@link UserWishlistGame} entity,
 * and to map a {@link UserWishlistGame} entity to a {@link UserWishlistGameResponseDTO} for response purposes.
 * </p>
 *
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.dto.response.UserWishlistGameResponseDTO;
import br.com.gamehub.model.*;

public class UserWishlistGameMapper {

    /**
     * Converts a {@link User} and {@link Game} to a {@link UserWishlistGame} entity.
     *
     * @param user The {@link User} associated with the wishlist game.
     * @param game The {@link Game} to be added to the user's wishlist.
     * @return The corresponding {@link UserWishlistGame} entity.
     */
    public static UserWishlistGame toEntity(User user, Game game) {
        UserWishlistGame wishlistGame = new UserWishlistGame();
        wishlistGame.setUser(user);
        wishlistGame.setGame(game);

        return wishlistGame;
    }

    /**
     * Converts a {@link UserWishlistGame} entity to a {@link UserWishlistGameResponseDTO}.
     *
     * @param wishlistGame The {@link UserWishlistGame} entity to be converted.
     * @return The corresponding {@link UserWishlistGameResponseDTO}.
     */
    public static UserWishlistGameResponseDTO toResponse(UserWishlistGame wishlistGame) {
        return new UserWishlistGameResponseDTO(GameMapper.toResponse(wishlistGame.getGame()));
    }
}
