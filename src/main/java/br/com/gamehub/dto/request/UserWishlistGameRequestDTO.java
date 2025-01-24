package br.com.gamehub.dto.request;

/**
 * Data Transfer Object (DTO) representing the request for adding a game to a user's wishlist.
 * This DTO contains the game ID that the user wants to add to their wishlist.
 *
 * @param gameId the ID of the game to be added to the wishlist. It cannot be null.
 */
public record UserWishlistGameRequestDTO(
        Long gameId
) { }
