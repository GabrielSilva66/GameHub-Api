/**
 * DeveloperMapper
 * 
 * <p>
 * Utility class for mapping between {@link Developer} entity, {@link DeveloperRequestDTO} and {@link DeveloperResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.model.Developer;

public class DeveloperMapper {

   /**
    * Converts a {@link DeveloperRequestDTO} to a {@link Developer} entity.
    * 
    * @param developerRequestDTO The DTO to be converted.
    * @return The corresponding {@link Developer} entity.
    */
   public static Developer toEntity(DeveloperRequestDTO developerRequestDTO) {
      if (developerRequestDTO == null) {
         return null;
      }

      return Developer.builder()
            .name(developerRequestDTO.name())
            .yearOfFoundation(developerRequestDTO.yearOfFoundation())
            .hostCountry(developerRequestDTO.hostCountry())
            .build();
   }

   /**
    * Converts a {@link Developer} entity to a {@link DeveloperResponseDTO}.
    * 
    * @param developer The {@link Developer} entity to be converted.
    * @return The corresponding {@link DeveloperResponseDTO}.
    */
   public static DeveloperResponseDTO toResponse(Developer developer) {
      if (developer == null) {
         return null;
      }

      return new DeveloperResponseDTO(
            developer.getId(),
            developer.getName(),
            developer.getYearOfFoundation(),
            developer.getHostCountry());
   }
}
