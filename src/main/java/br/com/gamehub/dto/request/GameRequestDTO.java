/**
 * GameRequestDTO
 * 
 * <p>
 * Data Transfer Object for Game requests. This DTO is used to transfer data when creating or updating a game.
 * It encapsulates the necessary information such as the developer ID, game name, release date, platform, and category details.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Developer ID is required and cannot be null.</li>
 *     <li>Game name is mandatory and cannot be blank.</li>
 *     <li>Release date must be less than or equal to the current date.</li>
 *     <li>Platform and Category details are optional. If not provided, default values are assigned.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import java.time.LocalDate;

import br.com.gamehub.validation.MaxDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a Game request containing essential information required to create
 * or update a game.
 * 
 * @param developerId            the ID of the developer. This field is
 *                               required.
 * @param name                   the name of the game. This field is mandatory.
 * @param releaseDate            the release date of the game. This field must
 *                               be less than or equal to the current date.
 * @param gamePlatformRequestDTO the platform details of the game. If null, a
 *                               default value will be assigned.
 * @param gameCategoryRequestDTO the category details of the game. If null, a
 *                               default value will be assigned.
 */
public record GameRequestDTO(
        @NotNull(message = "Developer ID is required") Long developerId,
      @NotBlank(message = "Game name is mandatory") String name,
      @MaxDate(message = "Release date must be less than or equal to the current date") LocalDate releaseDate,
      GamePlatformRequestDTO gamePlatformRequestDTO,
      GameCategoryRequestDTO gameCategoryRequestDTO

) {

   /**
    * Constructor to ensure that if platform or category details are not provided,
    * default values are set.
    */
   public GameRequestDTO {
      if (gamePlatformRequestDTO == null) {
         gamePlatformRequestDTO = new GamePlatformRequestDTO(null);
      }
      if (gameCategoryRequestDTO == null) {
         gameCategoryRequestDTO = new GameCategoryRequestDTO(null);
      }
   }
}
