package br.com.gamehub.dto.request;

import java.time.LocalDate;

import br.com.gamehub.validation.MaxDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameRequestDTO(
      @NotNull(message = "Developer ID is required") Long idDeveloper,
      @NotBlank(message = "Game name is required") String name,
      @MaxDate LocalDate releaseDate) {
}
