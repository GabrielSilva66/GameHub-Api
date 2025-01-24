package br.com.gamehub.controller;

import br.com.gamehub.dto.request.AssessmentRequestDTO;
import br.com.gamehub.dto.response.AssessmentResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.exception.InvalidRequestException; // Custom exception for invalid data
import br.com.gamehub.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing game reviews.
 * Provides endpoints for creating, retrieving, and updating game reviews.
 */
@RestController
@RequestMapping("/games")
public class AssessmentController {

    private final AssessmentService assessmentService;

    /**
     * Constructor for the controller.
     *
     * @param assessmentService The assessment service to be injected by Spring.
     */
    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * Creates a review for a specific game by a user.
     *
     * This endpoint receives a game ID, a user ID, and the review data to create a new
     * review in the system. The review is created successfully if the user and game exist.
     *
     * @param gameId The ID of the game to be reviewed.
     * @param userId The ID of the user creating the review.
     * @param dto The review data (rating and comment).
     * @return A response containing the created review with status 201 (Created).
     * @throws EntityNotFoundException If the game or user is not found.
     * @throws InvalidRequestException If the provided data is invalid.
     */
    @PostMapping("/{gameId}/feedback/{userId}")
    public ResponseEntity<AssessmentResponseDTO> createAssessment(@PathVariable Long gameId,
                                                                  @PathVariable Long userId,
                                                                  @Valid @RequestBody AssessmentRequestDTO dto) {
        // Calls the service to create the review
        AssessmentResponseDTO gameResponseDTO = assessmentService.createAssessment(userId, gameId, dto);
        return new ResponseEntity<>(gameResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves all reviews made by a specific user.
     *
     * This endpoint returns all reviews made by a specific user. If the user hasn't made
     * any reviews, the response will return HTTP status 204 (No Content).
     *
     * @param userId The ID of the user whose reviews will be returned.
     * @return A list of reviews made by the user with status 200 (OK).
     */
    @GetMapping("/feedback/{userId}")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentByIdUser(@PathVariable Long userId) {
        List<AssessmentResponseDTO> assessmentsDTO = assessmentService.getAssessmentsByUser(userId);

        if (assessmentsDTO != null && !assessmentsDTO.isEmpty()) {
            return new ResponseEntity<>(assessmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Retrieves all reviews made for a specific game.
     *
     * This endpoint returns all reviews made for a specific game. If the game has no reviews,
     * the response will return HTTP status 204 (No Content).
     *
     * @param gameId The ID of the game whose reviews will be returned.
     * @return A list of reviews made for the game with status 200 (OK).
     */
    @GetMapping("/{gameId}/feedback")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessmentByIdGame(@PathVariable Long gameId) {
        List<AssessmentResponseDTO> assessmentsDTO = assessmentService.getAssessmentsByGame(gameId);

        if (assessmentsDTO != null && !assessmentsDTO.isEmpty()) {
            return new ResponseEntity<>(assessmentsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Updates an existing review made by a user for a specific game.
     *
     * This endpoint allows a user to update a previously submitted review for a game.
     * If the review doesn't exist, an error will be generated. The data provided in the
     * request body will be used to update the existing review.
     *
     * @param gameId The ID of the game whose review will be updated.
     * @param userId The ID of the user who made the review.
     * @param dto The new data for the review (rating and comment).
     * @return The updated review with status 200 (OK).
     * @throws EntityNotFoundException If the review is not found.
     * @throws InvalidRequestException If the provided data is invalid.
     */
    @PutMapping("/{gameId}/feedback/{userId}")
    public ResponseEntity<AssessmentResponseDTO> updateAssessment(@PathVariable Long gameId,
                                                                  @PathVariable Long userId,
                                                                  @Valid @RequestBody AssessmentRequestDTO dto) {
        // Calls the service to update the review
        AssessmentResponseDTO assessmentResponseDTO = assessmentService.updateAssessment(gameId, userId, dto);
        return new ResponseEntity<>(assessmentResponseDTO, HttpStatus.OK);
    }
}
