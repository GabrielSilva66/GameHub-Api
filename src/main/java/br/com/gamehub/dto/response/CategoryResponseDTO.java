/**
 * CategoryResponseDTO
 * 
 * <p>
 * Data Transfer Object for Category response. This DTO is used for transferring category data in responses.
 * </p>
 * 
 * <p>
 * The CategoryResponseDTO contains information about the category, including its ID and name.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

/**
 * Represents a category response with its ID and name.
 * 
 * @param id   the unique identifier of the category.
 * @param name the name of the category.
 */
public record CategoryResponseDTO(
            Long id,
            String name) {
}
