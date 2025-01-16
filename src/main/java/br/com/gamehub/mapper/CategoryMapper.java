package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.CategoryRequestDTO;
import br.com.gamehub.dto.response.CategoryResponseDTO;
import br.com.gamehub.model.Category;

public class CategoryMapper {
   public static Category toEntity(CategoryRequestDTO categoryRequestDTO) {
      if (categoryRequestDTO == null) {
         return null;
      }

      return Category.builder()
            .name(categoryRequestDTO.name())
            .build();
   }

   public static CategoryResponseDTO toResponse(Category category) {
      if (category == null) {
         return null;
      }

      return new CategoryResponseDTO(
            category.getId(),
            category.getName());
   }
}
