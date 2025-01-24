/**
 * Service for managing games in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search games.
 * It also provides functionality to add or remove categories and platforms from a game.
 * </p>
 * <p>
 * Games are associated with developers, categories, and platforms. This service ensures that
 * all related entities exist and handles relationships appropriately.
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

@Service
public class GameService {

      private final GameRepository gameRepository;
      private final DeveloperRepository developerRepository;
      private final CategoryRepository categoryRepository;
      private final PlatformRepository platformRepository;

      /**
       * Constructor for initializing the service with the necessary repositories.
       *
       * @param gameRepository      the repository for managing games.
       * @param developerRepository the repository for managing developers.
       * @param categoryRepository  the repository for managing categories.
       * @param platformRepository  the repository for managing platforms.
       */
      @Autowired
      public GameService(GameRepository gameRepository, DeveloperRepository developerRepository,
                  CategoryRepository categoryRepository, PlatformRepository platformRepository) {
            this.gameRepository = gameRepository;
            this.developerRepository = developerRepository;
            this.categoryRepository = categoryRepository;
            this.platformRepository = platformRepository;
      }

      /**
       * Creates a new game using the provided request DTO.
       * <p>
       * Ensures that the developer, categories, and platforms exist before creating
       * the game.
       * </p>
       *
       * @param gameRequestDTO the game request DTO containing the game details.
       * @return the response DTO of the created game.
       * @throws EntityNotFoundException if the developer, categories, or platforms
       *                                 are not found.
       */
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

            return GameMapper.toResponse(game);
      }

      /**
       * Retrieves a game by its ID.
       *
       * @param id the ID of the game to retrieve.
       * @return the response DTO of the game.
       * @throws EntityNotFoundException if no game with the provided ID is found.
       */
      public GameResponseDTO getGameById(Long id) {
            Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

            return GameMapper.toResponse(game);
      }

      /**
       * Retrieves all games in the system.
       *
       * @return a list of response DTOs of all games.
       */
      public List<GameResponseDTO> getAllGames() {
            List<Game> games = gameRepository.findAll();

            return games.stream().map(GameMapper::toResponse).toList();
      }

      /**
       * Updates an existing game using the provided ID and request DTO.
       * <p>
       * Ensures that the developer, categories, and platforms exist before updating
       * the game.
       * </p>
       *
       * @param id             the ID of the game to update.
       * @param gameRequestDTO the game request DTO containing the updated game
       *                       details.
       * @return the response DTO of the updated game.
       * @throws EntityNotFoundException if the game, developer, categories, or
       *                                 platforms are not found.
       */
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

            return GameMapper.toResponse(game);
      }

      /**
       * Deletes a game by its ID.
       *
       * @param id the ID of the game to delete.
       * @throws EntityNotFoundException if no game with the provided ID is found.
       */
      public void deleteGame(Long id) {
            Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));

            gameRepository.delete(game);
      }

      /**
       * Searches for games based on the provided parameters, including pagination and
       * sorting.
       *
       * @param name      the name filter for the search (can be partial).
       * @param page      the page number for pagination.
       * @param size      the size of each page for pagination.
       * @param orderBy   the field to order by.
       * @param direction the direction of sorting (asc or desc).
       * @return a page of response DTOs for the matching games.
       */
      public Page<GameResponseDTO> searchGames(
                  String name, Integer page, Integer size, String orderBy, String direction) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Game> games = gameRepository.search(name, pageable);

            return games.map(GameMapper::toResponse);
      }

      /**
       * Adds categories to an existing game.
       *
       * @param gameId                 the ID of the game to update.
       * @param gameCategoryRequestDTO the DTO containing the category IDs to add.
       * @return the response DTO of the updated game.
       */
      public GameResponseDTO addCategoriesToGame(Long gameId, GameCategoryRequestDTO gameCategoryRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));

            List<Category> categories = categoryRepository.findAllById(gameCategoryRequestDTO.categoryIds());

            game.getCategories().addAll(categories);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game);
      }

      /**
       * Removes categories from an existing game.
       *
       * @param gameId                 the ID of the game to update.
       * @param gameCategoryRequestDTO the DTO containing the category IDs to remove.
       * @return the response DTO of the updated game.
       * @throws EntityNotFoundException if the game or categories are not found.
       */
      public GameResponseDTO removeCategoriesFromGame(Long gameId, GameCategoryRequestDTO gameCategoryRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));

            List<Category> categories = categoryRepository.findAllById(gameCategoryRequestDTO.categoryIds());

            game.getCategories().removeAll(categories);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game);
      }

      /**
       * Adds platforms to an existing game.
       *
       * @param gameId                 the ID of the game to update.
       * @param gamePlatformRequestDTO the DTO containing the platform IDs to add.
       * @return the response DTO of the updated game.
       * @throws EntityNotFoundException if the game or platforms are not found.
       */
      public GameResponseDTO addPlatformsToGame(Long gameId, GamePlatformRequestDTO gamePlatformRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));

            List<Platform> platforms = platformRepository.findAllById(gamePlatformRequestDTO.platformIds());

            game.getPlatforms().addAll(platforms);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game);
      }

      /**
       * Removes platforms from an existing game.
       *
       * @param gameId                 the ID of the game to update.
       * @param gamePlatformRequestDTO the DTO containing the platform IDs to remove.
       * @return the response DTO of the updated game.
       * @throws EntityNotFoundException if the game or platforms are not found.
       */
      public GameResponseDTO removePlatformsFromGame(Long gameId, GamePlatformRequestDTO gamePlatformRequestDTO) {
            Game game = gameRepository.findById(gameId)
                        .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));

            List<Platform> platforms = platformRepository.findAllById(gamePlatformRequestDTO.platformIds());

            game.getPlatforms().removeAll(platforms);
            game = gameRepository.save(game);

            return GameMapper.toResponse(game);
      }

}
