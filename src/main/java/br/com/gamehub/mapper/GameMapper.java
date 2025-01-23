/**
 * GameMapper
 * 
 * <p>
 * Utility class for mapping between {@link Game} entity, {@link GameRequestDTO} and {@link GameResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import java.util.List;
import java.util.stream.Collectors;

import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.CategoryResponseDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.model.Category;
import br.com.gamehub.model.Developer;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Platform;

public class GameMapper {

   /**
    * Converts a {@link GameRequestDTO}, {@link Developer}, {@link List<Category>}
    * and {@link List<Platform>} to a {@link Game} entity.
    * 
    * @param gameRequestDTO The DTO to be converted.
    * @param developer      The {@link Developer} associated with the game.
    * @param categories     The list of {@link Category} associated with the game.
    * @param platforms      The list of {@link Platform} associated with the game.
    * @return The corresponding {@link Game} entity.
    * @throws IllegalArgumentException if the developer is null.
    */
   public static Game toEntity(GameRequestDTO gameRequestDTO, Developer developer, List<Category> categories,
         List<Platform> platforms) {
      if (gameRequestDTO == null) {
         return null;
      } else if (developer == null) {
         throw new IllegalArgumentException("Developer is required");
      }

      return Game.builder()
            .developer(developer)
            .name(gameRequestDTO.name())
            .releaseDate(gameRequestDTO.releaseDate())
            .categories(categories)
            .platforms(platforms)
            .build();
   }

   /**
    * Converts a {@link Game} entity to a {@link GameResponseDTO}.
    * 
    * @param game The {@link Game} entity to be converted.
    * @return The corresponding {@link GameResponseDTO}.
    */
   public static GameResponseDTO toResponse(Game game) {
      if (game == null) {
         return null;
      }

      DeveloperResponseDTO developerResponseDTO = DeveloperMapper.toResponse(game.getDeveloper());
      List<CategoryResponseDTO> categoryResponseDTOs = game.getCategories().stream()
            .map(CategoryMapper::toResponse)
            .collect(Collectors.toList());
      List<PlatformResponseDTO> platformResponseDTOs = game.getPlatforms().stream()
            .map(PlatformMapper::toResponse)
            .collect(Collectors.toList());

      return new GameResponseDTO(
            game.getId(),
            developerResponseDTO,
            game.getName(),
            game.getReleaseDate(),
            categoryResponseDTOs,
            platformResponseDTOs);
   }
}
