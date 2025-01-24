/**
 * PlatformResponseDTO
 * 
 * <p>
 * Data Transfer Object for Platform response. This DTO is used for transferring platform data in responses.
 * </p>
 * 
 * <p>
 * The PlatformResponseDTO contains information about the platform, including its ID and name.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

/**
 * Represents a platform response with its ID and name.
 * 
 * @param id   the unique identifier of the platform.
 * @param name the name of the platform.
 */
public record PlatformResponseDTO(
            Long id,
            String name) {
}
