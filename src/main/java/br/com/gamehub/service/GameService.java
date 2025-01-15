package br.com.gamehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.mapper.GameMapper;
import br.com.gamehub.model.Developer;
import br.com.gamehub.model.Game;
import br.com.gamehub.repository.DeveloperRepository;
import br.com.gamehub.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GameService {
   private final GameRepository gameRepository;
   private final DeveloperRepository developerRepository;

   @Autowired
   public GameService(GameRepository gameRepository, DeveloperRepository developerRepository) {
      this.gameRepository = gameRepository;
      this.developerRepository = developerRepository;
   }

   public GameResponseDTO createGame(GameRequestDTO gameRequestDTO) {
      Long developerId = gameRequestDTO.developerId();
      Developer developer = developerRepository.findById(developerId)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + developerId + " not found"));

      Game game = GameMapper.toEntity(gameRequestDTO, developer);
      game = gameRepository.save(game);

      return GameMapper.toResponse(game, developer);
   }

   public GameResponseDTO getGameById(Long id) {
      Game game = gameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

      return GameMapper.toResponse(game, game.getDeveloper());
   }

   public List<GameResponseDTO> getAllGames() {
      List<Game> games = gameRepository.findAll();

      return games.stream().map(game -> GameMapper.toResponse(game, game.getDeveloper())).toList();
   }

   public GameResponseDTO updateGame(Long id, GameRequestDTO gameRequestDTO) {
      Long developerId = gameRequestDTO.developerId();
      Developer developer = developerRepository.findById(developerId)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + developerId + " not found"));
      Game game = gameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

      game.setDeveloper(developer);
      game.setName(gameRequestDTO.name());
      game.setReleaseDate(gameRequestDTO.releaseDate());

      game = gameRepository.save(game);

      return GameMapper.toResponse(game, developer);
   }

   public void deleteGame(Long id) {
      Game game = gameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

      gameRepository.delete(game);
   }

   public Page<GameResponseDTO> searchGames(
         String name,
         Optional<Long> developerId,
         Integer page,
         Integer size,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(page, size, sort);

      Page<Game> games = gameRepository.search(name, developerId.orElse(null), pageable);

      return games.map(game -> GameMapper.toResponse(game, game.getDeveloper()));
   }
}
