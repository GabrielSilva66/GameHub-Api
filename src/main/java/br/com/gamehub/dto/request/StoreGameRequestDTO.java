package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StoreGameRequestDTO(
            @NotNull(message = "Store id cannot be null") Long storeId,
            @NotNull(message = "Game id cannot be null") Long gameId,
            @NotNull(message = "Price cannot be null") @Min(value = 0, message = "Price must be at least 0") Double price) {
}
