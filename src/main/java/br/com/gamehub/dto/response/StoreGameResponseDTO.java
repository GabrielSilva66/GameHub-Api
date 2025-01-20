package br.com.gamehub.dto.response;

public record StoreGameResponseDTO(
        StoreResponseDTO store,
        double price
) {}
