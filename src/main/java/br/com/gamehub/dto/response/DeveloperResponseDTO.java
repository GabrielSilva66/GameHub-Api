/**
 * DeveloperResponseDTO
 * 
 * <p>
 * Data Transfer Object for Developer response. This DTO is used for transferring developer data in responses.
 * </p>
 * 
 * <p>
 * The DeveloperResponseDTO contains information about the developer, including its ID, name, year of foundation, and host country.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

/**
 * Represents a developer response with its ID, name, year of foundation, and
 * host country.
 * 
 * @param id               the unique identifier of the developer.
 * @param name             the name of the developer.
 * @param yearOfFoundation the year when the developer was founded.
 * @param hostCountry      the country where the developer is based.
 */
public record DeveloperResponseDTO(
            Long id,
            String name,
            Integer yearOfFoundation,
            String hostCountry) {
}
