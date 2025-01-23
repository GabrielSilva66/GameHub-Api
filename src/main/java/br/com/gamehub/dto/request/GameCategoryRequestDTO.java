/**
 * GameCategoryRequestDTO
 * 
 * <p>
 * Data Transfer Object for GameCategory request. This DTO is used to transfer data when creating or updating
 * the category details associated with a game.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Category IDs are mandatory and cannot be null.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * Represents the request for the category details associated with a game.
 * 
 * @param categoryIds the list of category IDs associated with the game. This
 *                    list must not contain null values.
 */
public record GameCategoryRequestDTO(
      List<@NotNull(message = "Category ID cannot be null") Long> categoryIds) {

   /**
    * Constructor to ensure that if categoryIds is not provided, it is set to an
    * empty list.
    */
   public GameCategoryRequestDTO {
      if (categoryIds == null) {
         categoryIds = List.of();
      }
   }
}
