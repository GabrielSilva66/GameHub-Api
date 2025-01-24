/**
 * DeveloperController
 * 
 * <p>
 * Controller for Developer resource, exposing endpoints for 
 * searching, retrieving and listing developers.
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

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.service.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;

@Tag(name = "Developers", description = "Endpoints for developers")
@RestController
@RequestMapping("/developers")
@CrossOrigin
public class DeveloperController {
   private final DeveloperService developerService;

   /**
    * Constructor for DeveloperController.
    * 
    * @param developerService The service for developers.
    */
   @Autowired
   public DeveloperController(DeveloperService developerService) {
      this.developerService = developerService;
   }

   /**
    * Creates a new developer.
    * 
    * @param developerRequestDTO The request body containing the developer data.
    * @return A ResponseEntity containing the created developer.
    */
   @PostMapping
   @Operation(summary = "Create a new developer", description = "Endpoint for creating a new developer")
   @ApiResponse(responseCode = "201", description = "Developer created successfully", content = @Content(schema = @Schema(implementation = DeveloperResponseDTO.class)))
   public ResponseEntity<DeveloperResponseDTO> createDeveloper(
         @Valid @RequestBody DeveloperRequestDTO developerRequestDTO) {
      DeveloperResponseDTO developerResponseDTO = developerService.createDeveloper(developerRequestDTO);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a developer by its ID.
    * 
    * @param id The ID of the developer to retrieve.
    * @return A ResponseEntity containing the retrieved developer.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get developer by ID", description = "Endpoint for retrieves a developer by its ID")
   @ApiResponse(responseCode = "200", description = "Developer found.", content = @Content(schema = @Schema(implementation = DeveloperResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Developer not found.", content = @Content())
   public ResponseEntity<DeveloperResponseDTO> getDeveloperById(
         @Parameter(description = "Developer ID", example = "1") @PathVariable("id") Long id) {
      DeveloperResponseDTO developerResponseDTO = developerService.getDeveloperById(id);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.OK);

   }

   /**
    * Retrieves all developers.
    * 
    * @return A ResponseEntity containing a list of all developers.
    */
   @GetMapping
   @Operation(summary = "Get all developers", description = "Endpoint for retrieving all developers")
   @ApiResponse(responseCode = "200", description = "Developers found.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeveloperResponseDTO.class))))
   public ResponseEntity<List<DeveloperResponseDTO>> getAllDevelopers() {
      List<DeveloperResponseDTO> developerResponseDTOs = developerService.getAllDevelopers();

      return new ResponseEntity<>(developerResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates an existing developer.
    * 
    * @param id                  the ID of the developer to update.
    * @param developerRequestDTO the request body containing the updated developer
    *                            data.
    * @return a ResponseEntity containing the updated developer.
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update a developer", description = "Endpoint for updating a developer")
   @ApiResponse(responseCode = "200", description = "Developer updated successfully", content = @Content(schema = @Schema(implementation = DeveloperResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Developer not found.", content = @Content())
   public ResponseEntity<DeveloperResponseDTO> updateDeveloper(
         @Parameter(description = "Developer ID", example = "1") @PathVariable("id") Long id,
         @Valid @RequestBody DeveloperRequestDTO developerRequestDTO) {
      DeveloperResponseDTO developerResponseDTO = developerService.updateDeveloper(id, developerRequestDTO);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a developer by its ID.
    * 
    * @param id the ID of the developer to delete.
    * @return a ResponseEntity indicating the deletion status.
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete a developer", description = "Endpoint for deleting a developer")
   @ApiResponse(responseCode = "204", description = "Developer deleted successfully", content = @Content())
   @ApiResponse(responseCode = "404", description = "Developer not found.", content = @Content())
   public ResponseEntity<Void> deleteDeveloper(
         @Parameter(description = "Developer ID", example = "1") @PathVariable("id") Long id) {
      developerService.deleteDeveloper(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for developers based on the given criteria.
    *
    * @param name      the name of the developer to search for.
    * @param page      the page number to retrieve.
    * @param size      the page size to retrieve.
    * @param orderBy   the field to order by.
    * @param direction the direction of the ordering.
    * @return a ResponseEntity containing a page of DeveloperResponseDTOs.
    */
   @GetMapping("/search")
   @Operation(summary = "Search developers by name", description = "Endpoint for searches for developers by name.")
   @ApiResponse(responseCode = "200", description = "Developers found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<DeveloperResponseDTO>> searchDevelopers(
         @Parameter(description = "Developer name", example = "Square Enix") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Developer page number", example = "0") @RequestParam(defaultValue = "0") Integer page,
         @Parameter(description = "Developer page size", example = "10") @RequestParam(defaultValue = "10") Integer size,
         @Parameter(description = "Developer order by", example = "id_developer") @RequestParam(defaultValue = "id_developer") String orderBy,
         @Parameter(description = "Developer direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<DeveloperResponseDTO> developerResponseDTOs = developerService.searchDevelopers(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(developerResponseDTOs, HttpStatus.OK);
   }
}