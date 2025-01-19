package br.com.gamehub.dto.response;

import java.time.LocalDate;
import java.util.List;

public record GameResponseDTO(
      Long id,
      DeveloperResponseDTO developer,
      String name,
      LocalDate releaseDate,
      List<CategoryResponseDTO> categories,
      List<PlatformResponseDTO> platforms) {
}
