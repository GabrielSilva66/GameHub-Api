package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.model.Developer;
import br.com.gamehub.model.Game;

public class GameMapper {
   public static Game toEntity(GameRequestDTO gameRequestDTO, Developer developer) {
      if (gameRequestDTO == null) {
         return null;
      } else if (developer == null) {
         throw new IllegalArgumentException("Developer is required");
      }

      return Game.builder()
            .developer(developer)
            .name(gameRequestDTO.name())
            .releaseDate(gameRequestDTO.releaseDate())
            .build();
   }

   public static GameResponseDTO toResponse(Game game, Developer developer) {
      if (game == null) {
         return null;
      }

      DeveloperResponseDTO developerResponseDTO = DeveloperMapper.toResponse(developer);

      return new GameResponseDTO(
            game.getId(),
            developerResponseDTO,
            game.getName(),
            game.getReleaseDate());
   }

}
