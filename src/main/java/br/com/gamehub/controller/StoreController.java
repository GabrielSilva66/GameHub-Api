/**
 * StoreController
 *
 * <p>
 * Controller for managing stores. This class provides endpoints to create, retrieve,
 * update, delete, and search for stores.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.controller;

import br.com.gamehub.dto.request.StoreRequestDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.service.StoreService;
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

@Tag(name = "Stores", description = "Endpoints for managing stores.")
@RestController
@RequestMapping("/stores")
@CrossOrigin
public class StoreController {

   private final StoreService storeService;

   /**
    * Constructor for StoreController.
    *
    * @param storeService the service for managing stores.
    */
   @Autowired
   public StoreController(StoreService storeService) {
      this.storeService = storeService;
   }

   /**
    * Creates a new store.
    *
    * @param storeRequestDTO the details of the store to create.
    * @return a ResponseEntity containing the created store.
    */
   @PostMapping
   @Operation(summary = "Create a new store", description = "Endpoint for creating a new store.")
   @ApiResponse(responseCode = "201", description = "Store successfully created.", content = @Content(schema = @Schema(implementation = StoreResponseDTO.class)))
   public ResponseEntity<StoreResponseDTO> createStore(
         @Parameter(description = "Store details to create", required = true) @Valid @RequestBody StoreRequestDTO storeRequestDTO) {
      StoreResponseDTO storeResponseDTO = storeService.createStore(storeRequestDTO);
      return new ResponseEntity<>(storeResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a store by its ID.
    *
    * @param id the ID of the store.
    * @return a ResponseEntity containing the store or a NOT_FOUND status if not
    *         found.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Retrieve a store by ID", description = "Endpoint for retrieving a store by its ID.")
   @ApiResponse(responseCode = "200", description = "Store found.", content = @Content(schema = @Schema(implementation = StoreResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Store not found.", content = @Content())
   public ResponseEntity<StoreResponseDTO> getStoreById(
         @Parameter(description = "ID of the store to retrieve", example = "1", required = true) @PathVariable("id") Long id) {
      StoreResponseDTO storeResponseDTO = storeService.getStoreById(id);
      return new ResponseEntity<>(storeResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all stores.
    *
    * @return a ResponseEntity containing the list of stores.
    */
   @GetMapping
   @Operation(summary = "Retrieve all stores", description = "Endpoint for retrieving all stores.")
   @ApiResponse(responseCode = "200", description = "Stores retrieved successfully.", content = @Content(schema = @Schema(implementation = List.class)))
   public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
      List<StoreResponseDTO> storeResponseDTOs = storeService.getAllStores();
      return new ResponseEntity<>(storeResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates an existing store.
    *
    * @param id              the ID of the store to update.
    * @param storeRequestDTO the updated store details.
    * @return a ResponseEntity containing the updated store.
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update a store", description = "Endpoint for updating an existing store.")
   @ApiResponse(responseCode = "200", description = "Store updated successfully.", content = @Content(schema = @Schema(implementation = StoreResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Store not found.", content = @Content())
   public ResponseEntity<StoreResponseDTO> updateStore(
         @Parameter(description = "ID of the store to update", example = "1", required = true) @PathVariable("id") Long id,
         @Parameter(description = "Updated store details", required = true) @Valid @RequestBody StoreRequestDTO storeRequestDTO) {
      StoreResponseDTO storeResponseDTO = storeService.updateStore(id, storeRequestDTO);
      return new ResponseEntity<>(storeResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a store.
    *
    * @param id the ID of the store to delete.
    * @return a ResponseEntity with NO_CONTENT status.
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete a store", description = "Endpoint for deleting a store.")
   @ApiResponse(responseCode = "204", description = "Store deleted successfully.", content = @Content())
   @ApiResponse(responseCode = "404", description = "Store not found.", content = @Content())
   public ResponseEntity<Void> deleteStore(
         @Parameter(description = "ID of the store to delete", example = "1", required = true) @PathVariable("id") Long id) {
      storeService.deleteStore(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for stores based on criteria.
    *
    * @param name       the name of the store to search for (optional).
    * @param pageNumber the page number for pagination (default: 0).
    * @param pageSize   the size of the page for pagination (default: 10).
    * @param orderBy    the field to sort by (default: "id_store").
    * @param direction  the sort direction (asc/desc, default: "asc").
    * @return a ResponseEntity containing the paginated list of stores.
    */
   @GetMapping("/search")
   @Operation(summary = "Search for stores", description = "Endpoint for searching stores with optional filters and pagination.")
   @ApiResponse(responseCode = "200", description = "Stores retrieved successfully.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<StoreResponseDTO>> searchStores(
         @Parameter(description = "Name of the store to search for", example = "GameHub") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Page number for pagination", example = "0") @RequestParam(defaultValue = "0") Integer pageNumber,
         @Parameter(description = "Page size for pagination", example = "10") @RequestParam(defaultValue = "10") Integer pageSize,
         @Parameter(description = "Field to sort by", example = "id_store") @RequestParam(defaultValue = "id_store") String orderBy,
         @Parameter(description = "Sort direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<StoreResponseDTO> storeResponseDTOs = storeService.searchStores(name, pageNumber, pageSize, orderBy,
            direction);
      return new ResponseEntity<>(storeResponseDTOs, HttpStatus.OK);
   }
}
