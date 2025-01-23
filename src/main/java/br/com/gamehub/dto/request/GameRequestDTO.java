package br.com.gamehub.dto.request;

import java.time.LocalDate;

import br.com.gamehub.validation.MaxDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameRequestDTO(@NotNull(message = "Developer ID is required") Long developerId,
      @NotBlank(message = "Game name is mandatory") String name,
      @MaxDate(message = "Release date must be less than or equal to the current date") LocalDate releaseDate,
      GamePlatformRequestDTO gamePlatformRequestDTO,
      GameCategoryRequestDTO gameCategoryRequestDTO) {
   public GameRequestDTO {
      if (gamePlatformRequestDTO == null) {
         gamePlatformRequestDTO = new GamePlatformRequestDTO(null);
      }
      if (gameCategoryRequestDTO == null) {
         gameCategoryRequestDTO = new GameCategoryRequestDTO(null);
      }
   }
}
