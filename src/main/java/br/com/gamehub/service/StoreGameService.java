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

   @Autowired
   public StoreGameService(StoreGameRepository storeGameRepository, StoreRepository storeRepository,
         GameRepository gameRepository) {
      this.storeGameRepository = storeGameRepository;
      this.storeRepository = storeRepository;
      this.gameRepository = gameRepository;
   }

   public StoreGameResponseDTO createStoreGame(StoreGameRequestDTO storeGameRequestDTO) {
      Long storeId = storeGameRequestDTO.storeId();
      Long gameId = storeGameRequestDTO.gameId();

      Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new EntityNotFoundException("Store with id " + storeId + " not found"));
      Game game = gameRepository.findById(gameId).orElseThrow(
            () -> new EntityNotFoundException("Game with id " + gameId + " not found"));

      StoreGame storeGame = StoreGameMapper.toEntity(storeGameRequestDTO, store, game);
      storeGame = storeGameRepository.save(storeGame);

      return StoreGameMapper.toResponse(storeGame);
   }

   public StoreGameResponseDTO getStoreGameById(Long storeId, Long gameId) {
      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId)
            .orElseThrow(() -> new EntityNotFoundException("StoreGame with store id " + storeId
                  + " and game id " + gameId + " not found"));

      return StoreGameMapper.toResponse(storeGame);
   }

   public List<StoreGameResponseDTO> getStoreGamesByStoreId(Long storeId) {
      List<StoreGame> storeGames = storeGameRepository.findAllByStoreId(storeId);

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   public List<StoreGameResponseDTO> getStoreGamesByGameId(Long gameId) {
      List<StoreGame> storeGames = storeGameRepository.findAllByGameId(gameId);

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   public List<StoreGameResponseDTO> getAllStoreGames() {
      List<StoreGame> storeGames = storeGameRepository.findAll();

      return storeGames.stream().map(StoreGameMapper::toResponse).toList();
   }

   public StoreGameResponseDTO updateStoreGame(StoreGameRequestDTO storeGameRequestDTO) {
      Long storeId = storeGameRequestDTO.storeId();
      Long gameId = storeGameRequestDTO.gameId();

      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId).orElseThrow(
            () -> new EntityNotFoundException(
                  "StoreGame with store id " + storeId + " and game id " + gameId + " not found"));

      storeGame.setPrice(storeGameRequestDTO.price());
      storeGame = storeGameRepository.save(storeGame);

      return StoreGameMapper.toResponse(storeGame);
   }

   public void deleteStoreGame(Long storeId, Long gameId) {
      StoreGameId storeGameId = new StoreGameId(storeId, gameId);
      StoreGame storeGame = storeGameRepository.findById(storeGameId).orElseThrow(
            () -> new EntityNotFoundException(
                  "StoreGame with store id " + storeId + " and game id " + gameId + " not found"));

      storeGameRepository.delete(storeGame);
   }

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
