package br.com.gamehub.dto.response;

import java.time.LocalDate;

public record UserObtainedGameResponseDTO(
        GameResponseDTO game,
        String store,
        String statusGame,
        LocalDate obtainedAt
) {
}
