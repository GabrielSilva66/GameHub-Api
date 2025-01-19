package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record StoreGameRequestDTO(
      @NotEmpty Long storeId,
      @NotEmpty Long gameId,
      @NotEmpty @Min(0) Double price) {
}
