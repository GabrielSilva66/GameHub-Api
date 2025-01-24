/**
 * GameResponseDTO
 *
 * <p>
 * Data Transfer Object for Game response. This DTO is used for transferring game data in responses.
 * </p>
 *
 * <p>
 * The GameResponseDTO contains information about a game, including its ID, developer, name, release date, categories, and platforms associated with it.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the response for a game.
 * This DTO contains the game's details including its developer, name, release date, categories,
 * platforms, evaluation score, and total number of evaluations.
 *
 * @param id the unique identifier of the game.
 * @param developer the developer of the game, represented by a {@link DeveloperResponseDTO}.
 * @param name the name of the game.
 * @param releaseDate the release date of the game.
 * @param categories the list of categories that the game belongs to, represented by a list of {@link CategoryResponseDTO}.
 * @param platforms the list of platforms where the game is available, represented by a list of {@link PlatformResponseDTO}.
 * @param evaluation the average evaluation score of the game.
 * @param totalEvaluation the total number of evaluations received for the game.
 */
public record GameResponseDTO(
        Long id,
        DeveloperResponseDTO developer,
        String name,
        LocalDate releaseDate,
        List<CategoryResponseDTO> categories,
        List<PlatformResponseDTO> platforms,
        Double evaluation,
        Integer totalEvaluation
) {}
