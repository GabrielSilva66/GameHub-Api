/**
 * PlatformController
 *
 * <p>
 * Controller for managing Platforms. This class provides endpoints for creating,
 * retrieving, updating, deleting, and searching platforms.
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.service.PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Platforms", description = "Endpoints for managing platforms.")
@RestController
@RequestMapping("/platforms")
@CrossOrigin
public class PlatformController {

   private final PlatformService platformService;

   /**
    * Constructor for PlatformController.
    *
    * @param platformService the service for managing platforms.
    */
   @Autowired
   public PlatformController(PlatformService platformService) {
      this.platformService = platformService;
   }

   /**
    * Creates a new platform.
    *
    * @param platformRequestDTO the details of the platform to create.
    * @return a ResponseEntity containing the created platform.
    */
   @PostMapping
   @Operation(summary = "Create a new platform", description = "Endpoint for creating a new platform.")
   @ApiResponse(responseCode = "201", description = "Platform successfully created.", content = @Content(schema = @Schema(implementation = PlatformResponseDTO.class)))
   public ResponseEntity<PlatformResponseDTO> createPlatform(
         @Valid @RequestBody PlatformRequestDTO platformRequestDTO) {
      PlatformResponseDTO platformResponseDTO = platformService.createPlatform(platformRequestDTO);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a platform by its ID.
    *
    * @param id the ID of the platform.
    * @return a ResponseEntity containing the platform.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Retrieve a platform by ID", description = "Endpoint for retrieving a platform by its ID.")
   @ApiResponse(responseCode = "200", description = "Platform found.", content = @Content(schema = @Schema(implementation = PlatformResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Platform not found.", content = @Content())
   public ResponseEntity<PlatformResponseDTO> getPlatformById(
         @Parameter(description = "Platform ID", example = "1") @PathVariable("id") Long id) {
      PlatformResponseDTO platformResponseDTO = platformService.getPlatformById(id);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all platforms.
    *
    * @return a ResponseEntity containing a list of all platforms.
    */
   @GetMapping
   @Operation(summary = "Retrieve all platforms", description = "Endpoint for retrieving all platforms.")
   @ApiResponse(responseCode = "200", description = "Platforms retrieved successfully.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlatformResponseDTO.class))))
   public ResponseEntity<List<PlatformResponseDTO>> getAllPlatforms() {
      List<PlatformResponseDTO> platformResponseDTOs = platformService.getAllPlatforms();

      return new ResponseEntity<>(platformResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates a platform by its ID.
    *
    * @param id                 the ID of the platform to update.
    * @param platformRequestDTO the updated details of the platform.
    * @return a ResponseEntity containing the updated platform.
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update a platform", description = "Endpoint for updating a platform by its ID.")
   @ApiResponse(responseCode = "200", description = "Platform updated successfully.", content = @Content(schema = @Schema(implementation = PlatformResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Platform not found.", content = @Content())
   public ResponseEntity<PlatformResponseDTO> updatePlatform(
         @Parameter(description = "Platform ID", example = "1") @PathVariable("id") Long id,
         @Valid @RequestBody PlatformRequestDTO platformRequestDTO) {
      PlatformResponseDTO platformResponseDTO = platformService.updatePlatform(id, platformRequestDTO);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a platform by its ID.
    *
    * @param id the ID of the platform to delete.
    * @return a ResponseEntity with no content.
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete a platform", description = "Endpoint for deleting a platform by its ID.")
   @ApiResponse(responseCode = "204", description = "Platform deleted successfully.", content = @Content())
   @ApiResponse(responseCode = "404", description = "Platform not found.", content = @Content())
   public ResponseEntity<Void> deletePlatform(
         @Parameter(description = "Platform ID", example = "1") @PathVariable("id") Long id) {
      platformService.deletePlatform(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for platforms by name with pagination and sorting options.
    *
    * @param name      the name of the platform to search.
    * @param page      the page number to retrieve.
    * @param size      the number of items per page.
    * @param orderBy   the field to sort by.
    * @param direction the sort direction (asc or desc).
    * @return a ResponseEntity containing the paginated search results.
    */
   @GetMapping("/search")
   @Operation(summary = "Search platforms by name", description = "Endpoint for searching platforms by name.")
   @ApiResponse(responseCode = "200", description = "Platforms found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<PlatformResponseDTO>> searchPlatforms(
         @Parameter(description = "Platform name", example = "Xbox") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") Integer page,
         @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") Integer size,
         @Parameter(description = "Field to sort by", example = "id_platform") @RequestParam(defaultValue = "id_platform") String orderBy,
         @Parameter(description = "Sort direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<PlatformResponseDTO> platformResponseDTOs = platformService.searchPlatforms(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(platformResponseDTOs, HttpStatus.OK);
   }
}
