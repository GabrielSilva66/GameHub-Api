/**
 * StoreGameController
 *
 * <p>
 * Controller for managing store-game relationships. This controller provides endpoints to create, retrieve, 
 * update, delete, and search for store-game associations.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.controller;

import br.com.gamehub.dto.request.StoreGameRequestDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.service.StoreGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Store-Games", description = "Endpoints for managing store-game associations.")
@RestController
@RequestMapping("store-games")
@CrossOrigin
public class StoreGameController {

   private final StoreGameService storeGameService;

   /**
    * Constructor for StoreGameController.
    *
    * @param storeGameService the service for managing store-game associations.
    */
   @Autowired
   public StoreGameController(StoreGameService storeGameService) {
      this.storeGameService = storeGameService;
   }

   /**
    * Creates a new store-game association.
    *
    * @param storeGameRequestDTO the details of the store-game association to
    *                            create.
    * @return a ResponseEntity containing the created store-game association.
    */
   @PostMapping
   @Operation(summary = "Create a new store-game association", description = "Endpoint for creating a new store-game association.")
   @ApiResponse(responseCode = "201", description = "Store-game successfully created.", content = @Content(schema = @Schema(implementation = StoreGameResponseDTO.class)))
   public ResponseEntity<StoreGameResponseDTO> createStoreGame(
         @Parameter(description = "Store-game details to create", required = true) @Valid @RequestBody StoreGameRequestDTO storeGameRequestDTO) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.createStoreGame(storeGameRequestDTO);
      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a store-game association by store ID and game ID.
    *
    * @param storeId the ID of the store.
    * @param gameId  the ID of the game.
    * @return a ResponseEntity containing the store-game association.
    */
   @GetMapping("/{storeId}/{gameId}")
   @Operation(summary = "Retrieve a store-game association by store and game ID", description = "Endpoint for retrieving a store-game association by store and game ID.")
   @ApiResponse(responseCode = "200", description = "Store-game found.", content = @Content(schema = @Schema(implementation = StoreGameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Store-game not found.", content = @Content())
   public ResponseEntity<StoreGameResponseDTO> getStoreGameById(
         @Parameter(description = "Store ID", required = true) @PathVariable("storeId") Long storeId,
         @Parameter(description = "Game ID", required = true) @PathVariable("gameId") Long gameId) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.getStoreGameById(storeId, gameId);
      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all store-games by store ID.
    *
    * @param storeId the ID of the store.
    * @return a ResponseEntity containing the list of store-games.
    */
   @GetMapping("/store/{storeId}")
   @Operation(summary = "Retrieve store-games by store ID", description = "Endpoint for retrieving store-games associated with a specific store.")
   @ApiResponse(responseCode = "200", description = "Store-games found.", content = @Content(schema = @Schema(implementation = List.class)))
   public ResponseEntity<List<StoreGameResponseDTO>> getStoreGamesByStoreId(
         @Parameter(description = "Store ID", required = true) @PathVariable("storeId") Long storeId) {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getStoreGamesByStoreId(storeId);
      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   /**
    * Retrieves all store-games by game ID.
    *
    * @param gameId the ID of the game.
    * @return a ResponseEntity containing the list of store-games.
    */
   @GetMapping("/game/{gameId}")
   @Operation(summary = "Retrieve store-games by game ID", description = "Endpoint for retrieving store-games associated with a specific game.")
   @ApiResponse(responseCode = "200", description = "Store-games found.", content = @Content(schema = @Schema(implementation = List.class)))
   public ResponseEntity<List<StoreGameResponseDTO>> getStoreGamesByGameId(
         @Parameter(description = "Game ID", required = true) @PathVariable("gameId") Long gameId) {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getStoreGamesByGameId(gameId);
      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   /**
    * Retrieves all store-game associations.
    *
    * @return a ResponseEntity containing the list of all store-games.
    */
   @GetMapping
   @Operation(summary = "Retrieve all store-game associations", description = "Endpoint for retrieving all store-game associations.")
   @ApiResponse(responseCode = "200", description = "Store-games retrieved successfully.", content = @Content(schema = @Schema(implementation = List.class)))
   public ResponseEntity<List<StoreGameResponseDTO>> getAllStoreGames() {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getAllStoreGames();
      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates an existing store-game association.
    *
    * @param storeGameRequestDTO the updated details of the store-game association.
    * @return a ResponseEntity containing the updated store-game association.
    */
   @PutMapping
   @Operation(summary = "Update a store-game association", description = "Endpoint for updating an existing store-game association.")
   @ApiResponse(responseCode = "200", description = "Store-game updated successfully.", content = @Content(schema = @Schema(implementation = StoreGameResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Store-game not found.", content = @Content())
   public ResponseEntity<StoreGameResponseDTO> updateStoreGame(
         @Parameter(description = "Updated store-game details", required = true) @Valid @RequestBody StoreGameRequestDTO storeGameRequestDTO) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.updateStoreGame(storeGameRequestDTO);
      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a store-game association.
    *
    * @param storeId the ID of the store.
    * @param gameId  the ID of the game.
    * @return a ResponseEntity with NO_CONTENT status.
    */
   @DeleteMapping("/{storeId}/{gameId}")
   @Operation(summary = "Delete a store-game association", description = "Endpoint for deleting a store-game association.")
   @ApiResponse(responseCode = "204", description = "Store-game deleted successfully.", content = @Content())
   @ApiResponse(responseCode = "404", description = "Store-game not found.", content = @Content())
   public ResponseEntity<Void> deleteStoreGame(
         @Parameter(description = "Store ID", required = true) @PathVariable("storeId") Long storeId,
         @Parameter(description = "Game ID", required = true) @PathVariable("gameId") Long gameId) {
      storeGameService.deleteStoreGame(storeId, gameId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for store-games based on various criteria such as store ID, game ID,
    * and price range.
    *
    * @param storeId    the ID of the store (optional).
    * @param gameId     the ID of the game (optional).
    * @param minPrice   the minimum price of the game (optional).
    * @param maxPrice   the maximum price of the game (optional).
    * @param pageNumber the page number for pagination (default: 0).
    * @param pageSize   the size of the page for pagination (default: 10).
    * @param orderBy    the field to sort by (default: "id_game").
    * @param direction  the sort direction (asc/desc, default: "asc").
    * @return a ResponseEntity containing the paginated list of store-games
    *         matching the search criteria.
    */
   @GetMapping("/search")
   @Operation(summary = "Search for store-games", description = "Endpoint for searching store-games with optional filters such as store ID, game ID, and price range.")
   @ApiResponse(responseCode = "200", description = "Store-games retrieved successfully.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<StoreGameResponseDTO>> searchStoreGames(
         @Parameter(description = "Store ID to search for (optional)") @RequestParam(required = false) Long storeId,
         @Parameter(description = "Game ID to search for (optional)") @RequestParam(required = false) Long gameId,
         @Parameter(description = "Minimum price to search for (optional)") @RequestParam(required = false) Double minPrice,
         @Parameter(description = "Maximum price to search for (optional)") @RequestParam(required = false) Double maxPrice,
         @Parameter(description = "Page number for pagination", example = "0") @RequestParam(defaultValue = "0") Integer pageNumber,
         @Parameter(description = "Page size for pagination", example = "10") @RequestParam(defaultValue = "10") Integer pageSize,
         @Parameter(description = "Field to sort by", example = "id_game") @RequestParam(defaultValue = "id_game") String orderBy,
         @Parameter(description = "Sort direction (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.searchStoreGames(storeId, gameId, minPrice,
            maxPrice, pageNumber, pageSize, orderBy, direction);
      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }
}
