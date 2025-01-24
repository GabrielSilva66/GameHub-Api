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
 * Represents a game response with its ID, developer, name, release date,
 * categories, and platforms.
 * 
 * @param id          the unique identifier of the game.
 * @param developer   the developer who created the game.
 * @param name        the name of the game.
 * @param releaseDate the release date of the game.
 * @param categories  the list of categories that the game belongs to.
 * @param platforms   the list of platforms on which the game is available.
 */
public record GameResponseDTO(
            Long id,
            DeveloperResponseDTO developer,
            String name,
            LocalDate releaseDate,
            List<CategoryResponseDTO> categories,
            List<PlatformResponseDTO> platforms) {
}
