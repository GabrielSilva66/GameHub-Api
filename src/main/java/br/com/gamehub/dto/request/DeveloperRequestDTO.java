/**
 * DeveloperRequestDTO
 * 
 * <p>
 * Data Transfer Object for Developer requests. This DTO is used for transferring data when creating or updating a game developer.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Developer name is mandatory and cannot be empty.</li>
 *     <li>Year of foundation must be greater than 1900 and less than or equal to the current year.</li>
 *     <li>Host country is optional and can be null.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import br.com.gamehub.validation.MaxYear;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a developer request with validation rules.
 * 
 * @param name the name of the developer (cannot be empty).
 * @param yearOfFoundation the year when the developer was founded (must be > 1900 and <= current year).
 * @param hostCountry the host country of the developer (optional).
 */
public record DeveloperRequestDTO(
      @NotBlank(message = "Name is mandatory") String name,
      @Min(value = 1900, message = "Year of foundation must be greater than 1900") 
      @MaxYear(message = "Year of foundation must be less than or equal to the current year") Integer yearOfFoundation,
      String hostCountry) {
}
