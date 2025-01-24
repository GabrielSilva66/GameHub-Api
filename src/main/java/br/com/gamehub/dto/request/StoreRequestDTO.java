/**
 * StoreRequestDTO
 * 
 * <p>
 * Data Transfer Object for Store request. This DTO is used for transferring data when creating or updating a store.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Store name is mandatory and cannot be empty.</li>
 *     <li>Store URL is mandatory and must be a valid URL format.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a store request with validation rules.
 * 
 * @param name the name of the store (cannot be empty).
 * @param url  the URL of the store (must be a valid URL).
 */
public record StoreRequestDTO(
      @NotBlank(message = "Name is mandatory") String name,
      @NotBlank(message = "URL is mandatory") @URL(message = "Invalid URL") String url) {
}
