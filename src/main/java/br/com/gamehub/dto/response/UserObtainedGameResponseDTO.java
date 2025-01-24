package br.com.gamehub.dto.response;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the response for a user's obtained game.
 * This DTO contains details about the game obtained by the user, the store where it was acquired,
 * the status of the game, and the date the game was obtained.
 *
 * @param game the game details.
 * @param store the name of the store where the game was obtained.
 * @param statusGame the current status of the obtained game.
 * @param obtainedAt the date when the game was obtained by the user.
 */
public record UserObtainedGameResponseDTO(
        GameResponseDTO game,
        String store,
        String statusGame,
        LocalDate obtainedAt
) {}
