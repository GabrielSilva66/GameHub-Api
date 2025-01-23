/**
 * PlatformRequestDTO
 * 
 * <p>
 * Data Transfer Object for Platform request. This DTO is used for transferring data when creating or updating a platform.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Platform name is mandatory and cannot be empty.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a platform request with validation rules.
 * 
 * @param name the name of the platform (cannot be empty).
 */
public record PlatformRequestDTO(
            @NotBlank(message = "Name is mandatory") String name) {
}
