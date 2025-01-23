/**
 * StoreGameRequestDTO
 * 
 * <p>
 * Data Transfer Object for StoreGame request. This DTO is used for transferring data when associating a game with a store.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Store ID cannot be null.</li>
 *     <li>Game ID cannot be null.</li>
 *     <li>Price must be provided and must be at least 0.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a store-game request with validation rules.
 * 
 * @param storeId the ID of the store (cannot be null).
 * @param gameId  the ID of the game (cannot be null).
 * @param price   the price of the game in the store (cannot be null and must be
 *                at least 0).
 */
public record StoreGameRequestDTO(
      @NotNull(message = "Store id cannot be null") Long storeId,
      @NotNull(message = "Game id cannot be null") Long gameId,
      @NotNull(message = "Price cannot be null") @Min(value = 0, message = "Price must be at least 0") Double price) {
}
