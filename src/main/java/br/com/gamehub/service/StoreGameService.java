/**
 * Service for managing the association between stores and games in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search for store-game associations.
 * It interacts with {@link StoreGameRepository}, {@link StoreRepository}, and {@link GameRepository}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.StoreGameRequestDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.id.StoreGameId;
import br.com.gamehub.mapper.StoreGameMapper;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Store;
import br.com.gamehub.model.StoreGame;
import br.com.gamehub.repository.GameRepository;
import br.com.gamehub.repository.StoreGameRepository;
import br.com.gamehub.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StoreGameService {

   private final StoreGameRepository storeGameRepository;
   private final StoreRepository storeRepository;
   private final GameRepository gameRepository;

   /**
    * Constructor for initializing the service with the necessary repositories.
    *
    * @param storeGameRepository the repository for store-game associations.
    * @param storeRepository     the repository for stores.
    * @param gameRepository      the repository for games.
    */
   @Autowired
   public StoreGameService(StoreGameRepository storeGameRepository, StoreRepository storeRepository,
         GameRepository gameRepository) {
      this.storeGameRepository = storeGameRepository;
      this.storeRepository = storeRepository;
      this.gameRepository = gameRepository;
   }

   /**
    * Creates a new store-game association.
    *
    * @param storeGameRequestDTO the request DTO containing store-game details.
    * @return the response DTO of the created store-game association.
    * @throws EntityNotFoundException if the specified store or game does not
    *                                 exist.
    */
   public StoreGameResponseDTO createStoreGame(StoreGameRequestDTO storeGameRequestDTO) {
      Long storeId = storeGameRequestDTO.storeId();
      Long gameId = storeGameRequestDTO.gameId();

      Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new EntityNotFoundException("Store with id " + storeId + " not found"));
      Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));

      StoreGame storeGame = StoreGameMapper.toEntity(storeGameRequestDTO, store, game);
      storeGame = storeGameRepository.save(storeGame);

      return StoreGameMapper.toResponse(storeGame);
   }

   /**
    * Retrieves a store-game association by store ID and game ID.
    *
    * @param storeId the ID of the store.
    * @param gameId  the ID of the game.
    * @return the response DTO of the store-game association.
    * @throws EntityNotFoundException if the association is not found.
    */
   public StoreGameResponseDTO getStoreGameById(Long storeId, Long gameId) {
      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId)
            .orElseThrow(() -> new EntityNotFoundException(
                  "StoreGame with store id " + storeId + " and game id " + gameId + " not found"));

      return StoreGameMapper.toResponse(storeGame);
   }

   /**
    * Retrieves all store-game associations for a specific store.
    *
    * @param storeId the ID of the store.
    * @return a list of response DTOs for the store-game associations.
    */
   public List<StoreGameResponseDTO> getStoreGamesByStoreId(Long storeId) {
      List<StoreGame> storeGames = storeGameRepository.findAllByStoreId(storeId);

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   /**
    * Retrieves all store-game associations for a specific game.
    *
    * @param gameId the ID of the game.
    * @return a list of response DTOs for the store-game associations.
    */
   public List<StoreGameResponseDTO> getStoreGamesByGameId(Long gameId) {
      List<StoreGame> storeGames = storeGameRepository.findAllByGameId(gameId);

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   /**
    * Retrieves all store-game associations in the system.
    *
    * @return a list of response DTOs for all store-game associations.
    */
   public List<StoreGameResponseDTO> getAllStoreGames() {
      List<StoreGame> storeGames = storeGameRepository.findAll();

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   /**
    * Updates an existing store-game association.
    *
    * @param storeGameRequestDTO the request DTO containing the updated details.
    * @return the response DTO of the updated store-game association.
    * @throws EntityNotFoundException if the store-game association is not found.
    */
   public StoreGameResponseDTO updateStoreGame(StoreGameRequestDTO storeGameRequestDTO) {
      Long storeId = storeGameRequestDTO.storeId();
      Long gameId = storeGameRequestDTO.gameId();

      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId)
            .orElseThrow(() -> new EntityNotFoundException(
                  "StoreGame with store id " + storeId + " and game id " + gameId + " not found"));

      storeGame.setPrice(storeGameRequestDTO.price());
      storeGame = storeGameRepository.save(storeGame);

      return StoreGameMapper.toResponse(storeGame);
   }

   /**
    * Deletes a store-game association by store ID and game ID.
    *
    * @param storeId the ID of the store.
    * @param gameId  the ID of the game.
    * @throws EntityNotFoundException if the store-game association is not found.
    */
   public void deleteStoreGame(Long storeId, Long gameId) {
      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId)
            .orElseThrow(() -> new EntityNotFoundException(
                  "StoreGame with store id " + storeId + " and game id " + gameId + " not found"));

      storeGameRepository.delete(storeGame);
   }

   /**
    * Searches for store-game associations based on various criteria, with
    * pagination and sorting.
    *
    * @param storeId    the store ID filter (optional).
    * @param gameId     the game ID filter (optional).
    * @param minPrice   the minimum price filter (optional).
    * @param maxPrice   the maximum price filter (optional).
    * @param pageNumber the page number for pagination.
    * @param pageSize   the size of each page for pagination.
    * @param orderBy    the field to order by.
    * @param direction  the direction of sorting (asc or desc).
    * @return a page of response DTOs for the matching store-game associations.
    */
   public Page<StoreGameResponseDTO> searchStoreGames(
         Long storeId,
         Long gameId,
         Double minPrice,
         Double maxPrice,
         Integer pageNumber,
         Integer pageSize,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<StoreGame> storeGames = storeGameRepository.search(storeId, gameId, minPrice, maxPrice, pageable);

      return storeGames.map(StoreGameMapper::toResponse);
   }
}
