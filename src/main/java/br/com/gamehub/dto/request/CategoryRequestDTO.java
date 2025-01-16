package br.com.gamehub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
      @NotBlank String name) {
}
