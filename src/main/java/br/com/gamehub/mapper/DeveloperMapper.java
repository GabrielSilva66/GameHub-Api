package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.model.Developer;

public class DeveloperMapper {
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
