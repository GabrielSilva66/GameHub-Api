package br.com.gamehub.dto.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record StoreRequestDTO(
            @NotBlank(message = "Name is mandatory") String name,
            @NotBlank(message = "URL is mandatory") @URL(message = "Invalid URL") String url) {
}
