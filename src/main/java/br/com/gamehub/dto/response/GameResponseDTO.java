package br.com.gamehub.dto.response;

import java.time.LocalDate;

public record GameResponseDTO(
            Long id,
            DeveloperResponseDTO developer,
            String name,
            LocalDate releaseDate) {
}
