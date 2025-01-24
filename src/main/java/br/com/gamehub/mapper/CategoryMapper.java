/**
 * CategoryMapper
 * 
 * <p>
 * Utility class for mapping between {@link Category} entity, {@link CategoryRequestDTO} and {@link CategoryResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.CategoryRequestDTO;
import br.com.gamehub.dto.response.CategoryResponseDTO;
import br.com.gamehub.model.Category;

public class CategoryMapper {

   /**
    * Converts a {@link CategoryRequestDTO} to a {@link Category} entity.
    * 
    * @param categoryRequestDTO The DTO to be converted.
    * @return The corresponding {@link Category} entity.
    */
   public static Category toEntity(CategoryRequestDTO categoryRequestDTO) {
      if (categoryRequestDTO == null) {
         return null;
      }

      return Category.builder()
            .name(categoryRequestDTO.name())
            .build();
   }

   /**
    * Converts a {@link Category} entity to a {@link CategoryResponseDTO}.
    * 
    * @param category The {@link Category} entity to be converted.
    * @return The corresponding {@link CategoryResponseDTO}.
    */
   public static CategoryResponseDTO toResponse(Category category) {
      if (category == null) {
         return null;
      }

      return new CategoryResponseDTO(
            category.getId(),
            category.getName());
   }
}
