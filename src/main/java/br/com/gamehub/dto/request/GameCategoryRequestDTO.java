package br.com.gamehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record GameCategoryRequestDTO(
      List<@NotNull(message = "Category ID cannot be null") Long> categoryIds) {
   public GameCategoryRequestDTO {
      if (categoryIds == null) {
         categoryIds = List.of();
      }
   }
}
