/**
 * StoreResponseDTO
 * 
 * <p>
 * Data Transfer Object for Store response. This DTO is used to represent a store, including its ID, name, and URL.
 * </p>
 * 
 * <p>
 * The StoreResponseDTO contains essential information about a store, such as its unique identifier, name, and URL.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

/**
 * Represents a store response with its ID, name, and URL.
 * 
 * @param id   the unique identifier of the store.
 * @param name the name of the store.
 * @param url  the URL of the store.
 */
public record StoreResponseDTO(
            Long id,
            String name,
            String url) {
}
