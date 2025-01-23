package br.com.gamehub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlatformRequestDTO(
      @NotBlank(message = "Name is mandatory") String name) {
}
