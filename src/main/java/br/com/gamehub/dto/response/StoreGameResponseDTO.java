package br.com.gamehub.dto.response;

public record StoreGameResponseDTO(
      StoreResponseDTO storeResponseDTO,
      GameResponseDTO gameResponseDTO,
      Double price) {
}
