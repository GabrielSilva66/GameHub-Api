package br.com.gamehub.dto.response;

public record DeveloperResponseDTO(
      Long id,
      String name,
      Integer yearOfFoundation,
      String hostCountry) {

}
