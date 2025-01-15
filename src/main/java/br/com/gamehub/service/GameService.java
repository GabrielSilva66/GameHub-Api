package br.com.gamehub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
      Long idDeveloper = gameRequestDTO.idDeveloper();
      Developer developer = developerRepository.findById(idDeveloper)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + idDeveloper + " not found"));

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
      Long idDeveloper = gameRequestDTO.idDeveloper();
      Developer developer = developerRepository.findById(idDeveloper)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + idDeveloper + " not found"));
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
}
