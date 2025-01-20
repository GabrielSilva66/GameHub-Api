package br.com.gamehub.dto.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record StoreRequestDTO(
      @NotBlank String name,
      @NotBlank @URL String url) {
}
