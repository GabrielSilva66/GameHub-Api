package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.StoreRequestDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.model.Store;

public class StoreMapper {
   public static Store toEntity(StoreRequestDTO storeRequestDTO) {
      if (storeRequestDTO == null) {
         return null;
      }

      return Store.builder()
            .name(storeRequestDTO.name())
            .url(storeRequestDTO.url())
            .build();
   }

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
