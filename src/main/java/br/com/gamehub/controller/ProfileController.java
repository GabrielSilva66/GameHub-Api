/**
 * ProfileController
 *
 * <p>
 * Controller for managing profiles. This class provides endpoints to create,
 * retrieve, update, and delete user profiles.
 * </p>
 *
 * @author Gabriel Silva
 * @since 2025-01-23
 */

package br.com.gamehub.controller;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.ProfileResponseDTO;
import br.com.gamehub.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Profiles", description = "Endpoints for managing user profiles.")
@RestController
@RequestMapping("/perfis")
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Constructor for ProfileController.
     *
     * @param profileService the service for managing profiles.
     */
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Creates a new profile.
     *
     * @param profileRequestDTO the details of the profile to create.
     * @return a ResponseEntity containing the created profile.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Create a new profile", description = "Endpoint for creating a new user profile.")
    @ApiResponse(responseCode = "201", description = "Profile successfully created.", content = @Content(schema = @Schema(implementation = ProfileResponseDTO.class)))
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @Parameter(description = "Profile details to create", required = true) @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO profileResponseDTO = profileService.createProfile(profileRequestDTO);
        return new ResponseEntity<>(profileResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves all profiles.
     *
     * @return a ResponseEntity containing the list of profiles.
     */
    @GetMapping
    @Operation(summary = "Retrieve all profiles", description = "Endpoint for retrieving all user profiles.")
    @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully.", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        List<ProfileResponseDTO> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    /**
     * Retrieves a profile by its ID.
     *
     * @param id the ID of the profile.
     * @return a ResponseEntity containing the profile or a NOT_FOUND status if not
     *         found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a profile by ID", description = "Endpoint for retrieving a profile by its ID.")
    @ApiResponse(responseCode = "200", description = "Profile found.", content = @Content(schema = @Schema(implementation = ProfileResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Profile not found.", content = @Content())
    public ResponseEntity<ProfileResponseDTO> getProfileById(
            @Parameter(description = "ID of the profile to retrieve", example = "1", required = true) @PathVariable("id") Long id) {
        ProfileResponseDTO profileResponseDTO = profileService.getProfileById(id);

        if (profileResponseDTO != null) {
            return new ResponseEntity<>(profileResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates an existing profile.
     *
     * @param id                the ID of the profile to update.
     * @param profileRequestDTO the updated profile details.
     * @return a ResponseEntity containing the updated profile or a NOT_FOUND status
     *         if not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a profile", description = "Endpoint for updating an existing user profile.")
    @ApiResponse(responseCode = "200", description = "Profile updated successfully.", content = @Content(schema = @Schema(implementation = ProfileResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Profile not found.", content = @Content())
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @Parameter(description = "ID of the profile to update", example = "1", required = true) @PathVariable("id") Long id,
            @Parameter(description = "Updated profile details", required = true) @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO updatedProfile = profileService.updateProfile(id, profileRequestDTO);

        if (updatedProfile != null) {
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a profile.
     *
     * @param id the ID of the profile to delete.
     * @return a ResponseEntity with NO_CONTENT status if deleted or NOT_FOUND if
     *         not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a profile", description = "Endpoint for deleting a user profile.")
    @ApiResponse(responseCode = "204", description = "Profile deleted successfully.", content = @Content())
    @ApiResponse(responseCode = "404", description = "Profile not found.", content = @Content())
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "ID of the profile to delete", example = "1", required = true) @PathVariable("id") Long id) {
        boolean isDeleted = profileService.deleteProfile(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
