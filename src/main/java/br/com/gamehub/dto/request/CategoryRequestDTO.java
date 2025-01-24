/**
 * CategoryRequestDTO
 * 
 * <p>
 * Data Transfer Object for Category request. This DTO is used for transferring data when creating or updating a game category.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Category name is mandatory and cannot be empty.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a category request with validation rules.
 * 
 * @param name the name of the category (cannot be empty).
 */
public record CategoryRequestDTO(
            @NotBlank(message = "Name is mandatory") String name) {
}
