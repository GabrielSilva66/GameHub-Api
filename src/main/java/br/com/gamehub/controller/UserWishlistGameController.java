package br.com.gamehub.controller;

import br.com.gamehub.dto.request.UserWishlistGameRequestDTO;
import br.com.gamehub.dto.response.UserWishlistGameResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.service.UserWishlistGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing the user's game wishlist.
 * Provides endpoints to add, retrieve, list, and delete games in a user's wishlist.
 */
@RestController
@RequestMapping("/usuarios")
public class UserWishlistGameController {
    private final UserWishlistGameService wishlistGameService;

    /**
     * Constructor for the UserWishlistGameController.
     *
     * @param wishlistGameService The service responsible for handling user's wishlist game logic.
     */
    @Autowired
    public UserWishlistGameController(UserWishlistGameService wishlistGameService) {
        this.wishlistGameService = wishlistGameService;
    }

    /**
     * Adds a game to the user's wishlist.
     *
     * This endpoint accepts a request body containing the game data and links it to the specified user.
     *
     * @param userid The ID of the user who wishes to add a game to their wishlist.
     * @param dto The data transfer object containing the game wishlist details.
     * @return A response containing the added game details, with status 201 (Created).
     */
    @PostMapping("/{userid}/jogos-desejados")
    public ResponseEntity<UserWishlistGameResponseDTO> createWishlistGame(@PathVariable Long userid,
                                                                          @Valid @RequestBody UserWishlistGameRequestDTO dto) {
        UserWishlistGameResponseDTO gameResponseDTO = wishlistGameService.createWishlistGame(userid, dto);
        return new ResponseEntity<>(gameResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific game from the user's wishlist by its ID.
     *
     * @param userid The ID of the user whose wishlist is being accessed.
     * @param wishlistGameId The ID of the game in the wishlist.
     * @return A response containing the wishlist game details, or 404 (Not Found) if the game is not found.
     */
    @GetMapping("/{userid}/jogos-desejados/{wishlistGameId}")
    public ResponseEntity<UserWishlistGameResponseDTO> getWishlistGameById(@PathVariable Long userid, @PathVariable Long wishlistGameId) {
        UserWishlistGameResponseDTO gameResponseDTO = wishlistGameService.getWishlistGameById(userid, wishlistGameId);

        if (gameResponseDTO != null) {
            return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves all the games in the user's wishlist.
     *
     * @param userid The ID of the user whose wishlist games are being retrieved.
     * @return A response containing a list of all the wishlist games for the user, with status 200 (OK).
     */
    @GetMapping("/{userid}/jogos-desejados")
    public ResponseEntity<List<UserWishlistGameResponseDTO>> getAllWishlistGames(@PathVariable Long userid) {
        List<UserWishlistGameResponseDTO> wishlistGameList = wishlistGameService.getAllWishlistGames(userid);
        return new ResponseEntity<>(wishlistGameList, HttpStatus.OK);
    }

    /**
     * Deletes a specific game from the user's wishlist.
     *
     * This endpoint removes a game from the wishlist using its ID for the given user.
     *
     * @param userid The ID of the user who wants to remove the game from their wishlist.
     * @param wishlistGameId The ID of the game to be deleted.
     * @return A response with status 204 (No Content) if the deletion is successful or 404 (Not Found) if the game is not found.
     */
    @DeleteMapping("/{userid}/jogos-desejados/{wishlistGameId}")
    public ResponseEntity<Void> deleteWishlistGame(@PathVariable Long userid, @PathVariable Long wishlistGameId) {
        try {
            wishlistGameService.deleteWishlistGame(userid, wishlistGameId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success, no content returned
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Game not found
        }
    }

}
