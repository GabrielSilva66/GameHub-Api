package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.StoreGameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Store;
import br.com.gamehub.model.StoreGame;

public class StoreGameMapper {
   public static StoreGame toEntity(StoreGameRequestDTO storeGameRequestDTO, Store store, Game game) {
      if (storeGameRequestDTO == null) {
         return null;
      } else if (store == null) {
         throw new IllegalArgumentException("Store is required");
      } else if (game == null) {
         throw new IllegalArgumentException("Game is required");
      }

      return StoreGame.builder()
            .store(store)
            .game(game)
            .price(storeGameRequestDTO.price())
            .build();
   }

   public static StoreGameResponseDTO toResponse(StoreGame storeGame) {
      if (storeGame == null) {
         return null;
      }

      StoreResponseDTO storeResponseDTO = StoreMapper.toResponse(storeGame.getStore());
      GameResponseDTO gameResponseDTO = GameMapper.toResponse(storeGame.getGame());

      return new StoreGameResponseDTO(
            storeResponseDTO,
            gameResponseDTO,
            storeGame.getPrice());
   }
}
