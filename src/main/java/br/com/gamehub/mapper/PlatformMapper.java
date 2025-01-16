package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.model.Platform;

public class PlatformMapper {
   public static Platform toEntity(PlatformRequestDTO platformRequestDTO) {
      if (platformRequestDTO == null) {
         return null;
      }

      return Platform.builder()
            .name(platformRequestDTO.name())
            .build();
   }

   public static PlatformResponseDTO toResponse(Platform platform) {
      if (platform == null) {
         return null;
      }

      return new PlatformResponseDTO(
            platform.getId(),
            platform.getName());
   }
}
