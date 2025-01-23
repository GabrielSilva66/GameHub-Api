/**
 * PlatformMapper
 * 
 * <p>
 * Utility class for mapping between {@link Platform} entity, {@link PlatformRequestDTO} and {@link PlatformResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.model.Platform;

public class PlatformMapper {

   /**
    * Converts a {@link PlatformRequestDTO} to a {@link Platform} entity.
    * 
    * @param platformRequestDTO The DTO to be converted.
    * @return The corresponding {@link Platform} entity.
    */
   public static Platform toEntity(PlatformRequestDTO platformRequestDTO) {
      if (platformRequestDTO == null) {
         return null;
      }

      return Platform.builder()
            .name(platformRequestDTO.name())
            .build();
   }

   /**
    * Converts a {@link Platform} entity to a {@link PlatformResponseDTO}.
    * 
    * @param platform The {@link Platform} entity to be converted.
    * @return The corresponding {@link PlatformResponseDTO}.
    */
   public static PlatformResponseDTO toResponse(Platform platform) {
      if (platform == null) {
         return null;
      }

      return new PlatformResponseDTO(
            platform.getId(),
            platform.getName());
   }
}
