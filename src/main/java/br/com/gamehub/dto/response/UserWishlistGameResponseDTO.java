package br.com.gamehub.dto.response;

/**
 * Data Transfer Object (DTO) representing the response for a user's wishlist game.
 * This DTO contains the game details that the user has added to their wishlist.
 *
 * @param game the game that is part of the user's wishlist.
 */
public record UserWishlistGameResponseDTO(
        GameResponseDTO game
) {}
