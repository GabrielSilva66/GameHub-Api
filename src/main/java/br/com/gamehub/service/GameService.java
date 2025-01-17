package br.com.gamehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.GameCategoryRequestDTO;
import br.com.gamehub.dto.request.GamePlatformRequestDTO;
import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.mapper.GameMapper;
import br.com.gamehub.model.Category;
import br.com.gamehub.model.Developer;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Platform;
import br.com.gamehub.repository.CategoryRepository;
import br.com.gamehub.repository.DeveloperRepository;
import br.com.gamehub.repository.GameRepository;
import br.com.gamehub.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;

/// TODO: Corrigir problemas de dependência em relações Many to Many (cascade)

@Service
public class GameService {
      private final GameRepository gameRepository;
      private final DeveloperRepository developerRepository;
      private final CategoryRepository categoryRepository;
      private final PlatformRepository platformRepository;

      @Autowired
      public GameService(GameRepository gameRepository, DeveloperRepository developerRepository,
                  CategoryRepository categoryRepository, PlatformRepository platformRepository) {
            this.gameRepository = gameRepository;
            this.developerRepository = developerRepository;
            this.categoryRepository = categoryRepository;
            this.platformRepository = platformRepository;
      }

      public GameResponseDTO createGame(GameRequestDTO gameRequestDTO) {
            Long developerId = gameRequestDTO.developerId();
            Developer developer = developerRepository.findById(developerId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                    "Developer with id " + developerId + " not found"));
            List<Category> categories = categoryRepository
                        .findAllById(gameRequestDTO.gameCategoryRequestDTO().categoryIds());
            List<Platform> platforms = platformRepository
                        .findAllById(gameRequestDTO.gamePlatformRequestDTO().platformIds());

            Game game = GameMapper.toEntity(gameRequestDTO, developer, categories, platforms);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game, developer, categories, platforms);
      }

      public GameResponseDTO getGameById(Long id) {
            Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

            return GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(), game.getPlatforms());
      }

      public List<GameResponseDTO> getAllGames() {
            List<Game> games = gameRepository.findAll();

            return games.stream()
                        .map(game -> GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(),
                                    game.getPlatforms()))
                        .toList();
      }

      public GameResponseDTO updateGame(Long id, GameRequestDTO gameRequestDTO) {
            Long developerId = gameRequestDTO.developerId();
            Developer developer = developerRepository.findById(developerId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                    "Developer with id " + developerId + " not found"));
            List<Category> categories = categoryRepository
                        .findAllById(gameRequestDTO.gameCategoryRequestDTO().categoryIds());
            List<Platform> platforms = platformRepository
                        .findAllById(gameRequestDTO.gamePlatformRequestDTO().platformIds());

            Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

            game.setDeveloper(developer);
            game.setName(gameRequestDTO.name());
            game.setReleaseDate(gameRequestDTO.releaseDate());
            game.setCategories(categories);
            game.setPlatforms(platforms);

            game = gameRepository.save(game);

            return GameMapper.toResponse(game, developer, categories, platforms);
      }

      public void deleteGame(Long id) {
            Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

            gameRepository.delete(game);
      }

      public Page<GameResponseDTO> searchGames(
                  String name,
                  Integer page,
                  Integer size,
                  String orderBy,
                  String direction) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Game> games = gameRepository.search(name, pageable);

            return games
                        .map(game -> GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(),
                                    game.getPlatforms()));
      }

      public GameResponseDTO addCategoriesToGame(Long gameId, GameCategoryRequestDTO gameCategoryRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
            List<Category> category = categoryRepository.findAllById(gameCategoryRequestDTO.categoryIds());

            game.getCategories().addAll(category);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(), game.getPlatforms());
      }

      public GameResponseDTO removeCategoriesFromGame(Long gameId, GameCategoryRequestDTO gameCategoryRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
            List<Category> category = categoryRepository.findAllById(gameCategoryRequestDTO.categoryIds());

            game.getCategories().removeAll(category);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(), game.getPlatforms());
      }

      public GameResponseDTO addPlatformsToGame(Long gameId, GamePlatformRequestDTO gamePlatformRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
            List<Platform> platform = platformRepository.findAllById(gamePlatformRequestDTO.platformIds());

            game.getPlatforms().addAll(platform);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(), game.getPlatforms());
      }

      public GameResponseDTO removePlatformsFromGame(Long gameId, GamePlatformRequestDTO gamePlatformRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
            List<Platform> platform = platformRepository.findAllById(gamePlatformRequestDTO.platformIds());

            game.getPlatforms().removeAll(platform);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game, game.getDeveloper(), game.getCategories(), game.getPlatforms());
      }
}
