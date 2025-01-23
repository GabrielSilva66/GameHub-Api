/**
 * GamePlatformRequestDTO
 * 
 * <p>
 * Data Transfer Object for GamePlatform request. This DTO is used to transfer data when creating or updating
 * the platform details associated with a game.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Platform IDs cannot be null.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * Represents the request for the platform details associated with a game.
 * 
 * @param platformIds the list of platform IDs associated with the game. This list must not contain null values.
 */
public record GamePlatformRequestDTO(
      List<@NotNull(message = "Platform ID cannot be null") Long> platformIds) {
   
   /**
    * Constructor to ensure that if platformIds is not provided, it is set to an empty list.
    */
   public GamePlatformRequestDTO {
      if (platformIds == null) {
         platformIds = List.of();
      }
   }
}
