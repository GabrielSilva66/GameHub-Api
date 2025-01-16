package br.com.gamehub.dto.request;

import java.time.LocalDate;
import java.util.List;

import br.com.gamehub.validation.MaxDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameRequestDTO(@NotNull(message = "Developer ID is required") Long developerId,
      @NotBlank(message = "Game name is required") String name, @MaxDate LocalDate releaseDate,
      List<@NotNull(message = "Category ID cannot be null") Long> categoryIds,
      List<@NotNull(message = "Platform ID cannot be null") Long> platformIds) {
}
