package br.com.gamehub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserObtainedGameRequestDTO(
        @NotNull(message = "Game ID cannot be null")
        Long id_game,

        @NotNull(message = "Store ID cannot be null")
        Long id_store,

        @NotNull(message = "Status cannot be null")
        String statusGame,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate obtainedAt
) {}