/**
 * StoreGameMapper
 * 
 * <p>
 * Utility class for mapping between {@link StoreGame} entity, {@link StoreGameRequestDTO} and {@link StoreGameResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.StoreGameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Store;
import br.com.gamehub.model.StoreGame;

public class StoreGameMapper {

   /**
    * Converts a {@link StoreGameRequestDTO}, {@link Store} and {@link Game} to a
    * {@link StoreGame} entity.
    * 
    * @param storeGameRequestDTO The DTO to be converted.
    * @param store               The {@link Store} associated with the game.
    * @param game                The {@link Game} associated with the store.
    * @return The corresponding {@link StoreGame} entity.
    * @throws IllegalArgumentException if the store or game is null.
    */
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

   /**
    * Converts a {@link StoreGame} entity to a {@link StoreGameResponseDTO}.
    * 
    * @param storeGame The {@link StoreGame} entity to be converted.
    * @return The corresponding {@link StoreGameResponseDTO}.
    */
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
