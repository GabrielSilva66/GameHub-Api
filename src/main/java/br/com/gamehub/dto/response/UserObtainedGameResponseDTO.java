package br.com.gamehub.dto.response;

import br.com.gamehub.model.Game;

import java.time.LocalDate;

public record UserObtainedGameResponseDTO(
        Game game,
        String store,
        String statusGame,
        LocalDate obtainedAt
) {
}
