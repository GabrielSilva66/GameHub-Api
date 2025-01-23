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
