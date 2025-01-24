package br.com.gamehub.controller;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.UserObtainedGameResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.service.UserObtainedGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing user's obtained games.
 * Provides endpoints to create, retrieve, list, and delete games acquired by a user.
 */
@RestController
@RequestMapping("/usuarios")
public class UserObtainedGameController {
    private final UserObtainedGameService obtainedGameService;

    /**
     * Constructor for the UserObtainedGameController.
     *
     * @param obtainedGameService The service responsible for handling user obtained game logic.
     */
    @Autowired
    public UserObtainedGameController(UserObtainedGameService obtainedGameService) {
        this.obtainedGameService = obtainedGameService;
    }

    /**
     * Creates a new game obtained by the user.
     *
     * This endpoint accepts a request body containing the game data and links it to the specified user.
     *
     * @param userId The ID of the user who obtained the game.
     * @param dto The data transfer object containing the game acquisition details.
     * @return A response containing the obtained game details, with status 201 (Created).
     */
    @PostMapping("/{userId}/jogos-adquiridos")
    public ResponseEntity<UserObtainedGameResponseDTO> createObtainedGame(@PathVariable Long userId,
                                                                          @Valid @RequestBody UserObtainedGameRequestDTO dto) {
        UserObtainedGameResponseDTO gameResponseDTO = obtainedGameService.createObtainedGame(userId, dto);
        return new ResponseEntity<>(gameResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific obtained game by its ID for a given user.
     *
     * @param userId The ID of the user who obtained the game.
     * @param obtainedGameId The ID of the obtained game.
     * @return A response containing the obtained game details, or 404 (Not Found) if the game is not found.
     */
    @GetMapping("/{userId}/jogos-adquiridos/{obtainedGameId}")
    public ResponseEntity<UserObtainedGameResponseDTO> getObtainedGameById(@PathVariable Long userId, @PathVariable Long obtainedGameId) {
        UserObtainedGameResponseDTO gameResponseDTO = obtainedGameService.getObtainedGameById(userId, obtainedGameId);

        if (gameResponseDTO != null) {
            return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves all obtained games for a specific user.
     *
     * @param userId The ID of the user to retrieve the obtained games for.
     * @return A response containing a list of all the obtained games for the user, with status 200 (OK).
     */
    @GetMapping("/{userId}/jogos-adquiridos")
    public ResponseEntity<List<UserObtainedGameResponseDTO>> getAllObtainedGames(@PathVariable Long userId) {
        List<UserObtainedGameResponseDTO> obtainedGameList = obtainedGameService.getAllObtainedGames(userId);
        return new ResponseEntity<>(obtainedGameList, HttpStatus.OK);
    }

    /**
     * Deletes a specific obtained game for a user.
     *
     * This endpoint deletes an obtained game by its ID for a specified user.
     *
     * @param userId The ID of the user who wants to delete the obtained game.
     * @param obtainedGameId The ID of the obtained game to be deleted.
     * @return A response with status 204 (No Content) if the deletion is successful or 404 (Not Found) if the game is not found.
     */
    @DeleteMapping("/{userId}/jogos-adquiridos/{obtainedGameId}")
    public ResponseEntity<Void> deleteObtainedGame(@PathVariable Long userId, @PathVariable Long obtainedGameId) {
        try {
            obtainedGameService.deleteObtainedGame(userId, obtainedGameId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success, no content returned
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Game not found
        }
    }
}
