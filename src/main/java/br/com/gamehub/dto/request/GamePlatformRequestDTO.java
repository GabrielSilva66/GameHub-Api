package br.com.gamehub.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record GamePlatformRequestDTO(
      List<@NotNull(message = "Platform ID cannot be null") Long> platformIds) {
   public GamePlatformRequestDTO {
      if (platformIds == null) {
         platformIds = List.of();
      }
   }
}
