package br.com.gamehub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the request for obtaining a game by a user.
 * This DTO holds the details about the game, store, status, and the date the game was obtained.
 *
 * @param id_game the ID of the game being obtained. It cannot be null.
 * @param id_store the ID of the store from which the game is obtained. It cannot be null.
 * @param statusGame the status of the game (e.g., "purchased", "downloaded"). It cannot be null.
 * @param obtainedAt the date when the game was obtained. It must follow the "yyyy-MM-dd" pattern.
 */
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