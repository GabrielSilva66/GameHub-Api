/**
 * StoreMapper
 * 
 * <p>
 * Utility class for mapping between {@link Store} entity, {@link StoreRequestDTO} and {@link StoreResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.StoreRequestDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.model.Store;

public class StoreMapper {

   /**
    * Converts a {@link StoreRequestDTO} to a {@link Store} entity.
    * 
    * @param storeRequestDTO The DTO to be converted.
    * @return The corresponding {@link Store} entity.
    */
   public static Store toEntity(StoreRequestDTO storeRequestDTO) {
      if (storeRequestDTO == null) {
         return null;
      }

      return Store.builder()
            .name(storeRequestDTO.name())
            .url(storeRequestDTO.url())
            .build();
   }

   /**
    * Converts a {@link Store} entity to a {@link StoreResponseDTO}.
    * 
    * @param store The {@link Store} entity to be converted.
    * @return The corresponding {@link StoreResponseDTO}.
    */
   public static StoreResponseDTO toResponse(Store store) {
      if (store == null) {
         return null;
      }

      return new StoreResponseDTO(
            store.getId(),
            store.getName(),
            store.getUrl());
   }
}
