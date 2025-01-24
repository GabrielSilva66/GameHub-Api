/**
 * StoreGameResponseDTO
 * 
 * <p>
 * Data Transfer Object for Store-Game relationship response. This DTO is used to represent the relationship between a store and a game,
 * including the associated price for that game at the store.
 * </p>
 * 
 * <p>
 * The StoreGameResponseDTO contains information about a store, a game, and the price at which the game is available in the store.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

/**
 * Represents the relationship between a store and a game along with the price
 * at which the game is available in the store.
 * 
 * @param storeResponseDTO the store where the game is available.
 * @param gameResponseDTO  the game that is available in the store.
 * @param price            the price of the game at the store.
 */
public record StoreGameResponseDTO(
            StoreResponseDTO storeResponseDTO,
            GameResponseDTO gameResponseDTO,
            Double price) {
}
