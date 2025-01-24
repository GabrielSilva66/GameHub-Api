/**
 * GameController
 * 
 * <p>
 * Controller for Game resource, exposing endpoints for
 * searching, retrieving and listing games.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.request.GameCategoryRequestDTO;
import br.com.gamehub.dto.request.GamePlatformRequestDTO;
import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Game", description = "Endpoints for Game resource")
@RestController
@RequestMapping("/games")
@CrossOrigin
public class GameController {
   private final GameService gameService;

   /**
    * GameController constructor
    * 
    * @param gameService service for Game resource
    */
   @Autowired
   public GameController(GameService gameService) {
      this.gameService = gameService;
   }

   /**
    * Creates a new game based on the provided request data.
    *
    * @param gameRequestDTO the request body containing the game data
    * @return a ResponseEntity containing the created game
    */
   @PostMapping
   @Operation(summary = "Create a new game", description = "Endpoint for creating a new game")
   @ApiResponse(responseCode = "201", description = "Game created successfully", content = @Content(schema = @Schema(implementation = GameResponseDTO.class)))
   public ResponseEntity<GameResponseDTO> createGame(@Valid @RequestBody GameRequestDTO gameRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.createGame(gameRequestDTO);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a game by its id.
    *
    * @param id the id of the game to be retrieved
    * @return a ResponseEntity containing the game
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get a game by id", description = "Endpoint for retrieving a game by its id")
   @ApiResponse(responseCode = "200", description = "Game retrieved successfully", content = @Content(schema = @Schema(implementation = GameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<GameResponseDTO> getGameById(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id) {
      GameResponseDTO gameResponseDTO = gameService.getGameById(id);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all games.
    *
    * @return a ResponseEntity containing the list of games
    */
   @GetMapping
   @Operation(summary = "Get all games", description = "Endpoint for retrieving all games")
   @ApiResponse(responseCode = "200", description = "Games retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class))))
   public ResponseEntity<List<GameResponseDTO>> getAllGames() {
      List<GameResponseDTO> gameResponseDTOs = gameService.getAllGames();

      return new ResponseEntity<List<GameResponseDTO>>(gameResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates an existing game with the provided request data.
    *
    * @param id             the id of the game to be updated
    * @param gameRequestDTO the request body containing the updated game data
    * @return a ResponseEntity containing the updated game
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update a game by id", description = "Endpoint for updating a game by its id")
   @ApiResponse(responseCode = "200", description = "Game updated successfully", content = @Content(schema = @Schema(implementation = GameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<GameResponseDTO> updateGame(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id,
         @Valid @RequestBody GameRequestDTO gameRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.updateGame(id, gameRequestDTO);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a game by its id.
    * 
    * @param id the id of the game to be deleted
    * @return a ResponseEntity containing a NO_CONTENT status
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete a game by id", description = "Endpoint for deleting a game by its id")
   @ApiResponse(responseCode = "204", description = "Game deleted.", content = @Content())
   @ApiResponse(responseCode = "404", description = "Game not found.", content = @Content())
   public ResponseEntity<Void> deleteGame(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id) {
      gameService.deleteGame(id);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for games with optional filtering and pagination.
    *
    * @param name      the name of the game to search for; defaults to an empty
    *                  string for no filtering
    * @param page      the page number for pagination; defaults to 0
    * @param size      the number of records per page; defaults to 10
    * @param orderBy   the field by which the results should be ordered; defaults
    *                  to "id_game"
    * @param direction the direction of sorting; defaults to "asc" for ascending
    *                  order
    * @return a ResponseEntity containing a page of GameResponseDTO objects
    */
   @GetMapping("/search")
   @Operation(summary = "Search for games", description = "Endpoint for searching for games with optional filtering and pagination")
   @ApiResponse(responseCode = "200", description = "Games found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<GameResponseDTO>> searchGames(
         @Parameter(description = "Game name", example = "Super Mario") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Game page", example = "0") @RequestParam(defaultValue = "0") Integer page,
         @Parameter(description = "Game size", example = "10") @RequestParam(defaultValue = "10") Integer size,
         @Parameter(description = "Game order by", example = "id_game") @RequestParam(defaultValue = "id_game") String orderBy,
         @Parameter(description = "Game order direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<GameResponseDTO> gameResponseDTOs = gameService.searchGames(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(gameResponseDTOs, HttpStatus.OK);
   }

   /**
    * Adds categories to an existing game.
    * 
    * @param id                     the id of the game to which the categories are
    *                               to be
    *                               added
    * @param gameCategoryRequestDTO the request body containing the category IDs
    * @return a ResponseEntity containing the updated game
    */
   @PatchMapping("/{id}/categories")
   @Operation(summary = "Add categories to a game", description = "Endpoint for adding categories to a game")
   @ApiResponse(responseCode = "200", description = "Categories added successfully", content = @Content(schema = @Schema(implementation = GameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<GameResponseDTO> addCategory(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id,
         @RequestBody GameCategoryRequestDTO gameCategoryRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.addCategoriesToGame(id, gameCategoryRequestDTO);

      return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK);
   }

   /**
    * Removes categories from an existing game.
    *
    * @param id                     the id of the game from which the categories
    *                               are to be removed
    * @param gameCategoryRequestDTO the request body containing the category IDs to
    *                               be removed
    * @return a ResponseEntity with a NO_CONTENT status indicating successful
    *         removal
    */
   @DeleteMapping("/{id}/categories")
   @Operation(summary = "Remove categories from a game", description = "Endpoint for removing categories from a game")
   @ApiResponse(responseCode = "204", description = "Categories removed successfully", content = @Content())
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<Void> removeCategory(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id,
         @RequestBody GameCategoryRequestDTO gameCategoryRequestDTO) {
      gameService.removeCategoriesFromGame(id, gameCategoryRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   /**
    * Adds platforms to an existing game.
    *
    * @param id                     the id of the game to which the platforms
    *                               are to be added
    * @param gamePlatformRequestDTO the request body containing the platform IDs to
    *                               be added
    * @return a ResponseEntity containing the updated game
    */
   @PatchMapping("/{id}/platforms")
   @Operation(summary = "Add platforms to a game", description = "Endpoint for adding platforms to a game")
   @ApiResponse(responseCode = "200", description = "Platforms added successfully", content = @Content(schema = @Schema(implementation = GameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<GameResponseDTO> addPlatform(
         @Parameter(description = "Game ID", example = "1") @PathVariable("id") Long id,
         GamePlatformRequestDTO gamePlatformRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.addPlatformsToGame(id, gamePlatformRequestDTO);

      return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK);
   }

   /**
    * Removes platforms from an existing game.
    *
    * @param id                     the id of the game from which the platforms
    *                               are to be removed
    * @param gamePlatformRequestDTO the request body containing the platform IDs to
    *                               be removed
    * @return a ResponseEntity with a NO_CONTENT status indicating successful
    *         removal
    */
   @DeleteMapping("/{id}/platforms")
   @Operation(summary = "Remove platforms from a game", description = "Endpoint for removing platforms from a game")
   @ApiResponse(responseCode = "204", description = "Platforms removed successfully", content = @Content())
   @ApiResponse(responseCode = "404", description = "Game not found", content = @Content())
   public ResponseEntity<Void> removePlatform(@Parameter (description = "Game ID", example = "1") @PathVariable("id") Long id,
         GamePlatformRequestDTO gamePlatformRequestDTO) {
      gameService.removePlatformsFromGame(id, gamePlatformRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }
}
