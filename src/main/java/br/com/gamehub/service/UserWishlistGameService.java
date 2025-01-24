/**
 * Service for managing the user's wishlist of games.
 * <p>
 * This service allows users to add games to their wishlist, view all games in their wishlist,
 * get details of specific games in the wishlist, and remove games from the wishlist.
 * It also ensures that a game cannot be added to the wishlist if it has already been obtained or is already in the wishlist.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import br.com.gamehub.dto.request.UserWishlistGameRequestDTO;
import br.com.gamehub.dto.response.UserWishlistGameResponseDTO;
import br.com.gamehub.exception.EntityAlreadyExistsException;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.UserWishlistGameMapper;
import br.com.gamehub.model.*;
import br.com.gamehub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWishlistGameService {

    private final UserRepository userRepository;
    private final UserWishlistGameRepository wishlistGameRepository;
    private final UserObtainedGameRepository obtainedGameRepository;
    private final GameRepository gameRepository;

    @Autowired
    public UserWishlistGameService(UserRepository userRepository, UserObtainedGameRepository obtainedGameRepository,
                                   UserWishlistGameRepository wishlistGameRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.wishlistGameRepository = wishlistGameRepository;
        this.obtainedGameRepository = obtainedGameRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Adds a game to the user's wishlist.
     *
     * @param userId the ID of the user.
     * @param wishlistGameDTO the game data transfer object containing the game to be added.
     * @return a response DTO with the details of the added game.
     * @throws EntityAlreadyExistsException if the game is already in the user's obtained games or wishlist.
     */
    public UserWishlistGameResponseDTO createWishlistGame(Long userId, UserWishlistGameRequestDTO wishlistGameDTO) {

        User user = findUserById(userId);
        Game game = findGameById(wishlistGameDTO.gameId());

        // Check if the user has already obtained the game
        boolean alreadyObtained = obtainedGameRepository.findByUserIdAndGameId(userId, wishlistGameDTO.gameId()).isPresent();
        if (alreadyObtained) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already obtained the game with id " + wishlistGameDTO.gameId());
        }

        // Check if the game is already in the user's wishlist
        boolean alreadyWishlist = wishlistGameRepository.findByUserIdAndGameId(userId, wishlistGameDTO.gameId()).isPresent();
        if (alreadyWishlist) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already wishlisted the game with id " + wishlistGameDTO.gameId());
        }

        // Add the game to the wishlist
        UserWishlistGame wishlistGame = UserWishlistGameMapper.toEntity(user, game);
        wishlistGame = wishlistGameRepository.save(wishlistGame);

        return UserWishlistGameMapper.toResponse(wishlistGame);
    }

    /**
     * Returns all games in the user's wishlist.
     *
     * @param userId the ID of the user.
     * @return a list of response DTOs with the details of all games in the user's wishlist.
     * @throws EntityNotFoundException if the user has no games in their wishlist.
     */
    public List<UserWishlistGameResponseDTO> getAllWishlistGames(Long userId) {
        List<UserWishlistGame> wishlistGames = wishlistGameRepository.findAllByUserId(userId);

        if (wishlistGames.isEmpty()) {
            throw new EntityNotFoundException("No wishlist games found for user with id: " + userId);
        }

        return wishlistGames.stream()
                .map(UserWishlistGameMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Returns details of a specific game in the user's wishlist.
     *
     * @param userId the ID of the user.
     * @param gameId the ID of the game.
     * @return a response DTO with the details of the game in the wishlist.
     * @throws EntityNotFoundException if the game is not in the user's wishlist.
     */
    public UserWishlistGameResponseDTO getWishlistGameById(Long userId, Long gameId) {
        UserWishlistGame wishlistGame = wishlistGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist game not found for user with id: " + userId + " and game id: " + gameId));

        return UserWishlistGameMapper.toResponse(wishlistGame);
    }

    /**
     * Removes a game from the user's wishlist.
     *
     * @param userId the ID of the user.
     * @param gameId the ID of the game.
     * @throws EntityNotFoundException if the game is not found in the user's wishlist.
     */
    public void deleteWishlistGame(Long userId, Long gameId) {
        UserWishlistGame wishlistGame = wishlistGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist game not found for user with id: " + userId + " and game id: " + gameId));

        wishlistGameRepository.delete(wishlistGame);
    }

    /**
     * Returns a user by their ID.
     *
     * @param id the ID of the user.
     * @return the user associated with the ID.
     * @throws EntityNotFoundException if the user is not found.
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Returns a game by its ID.
     *
     * @param id the ID of the game.
     * @return the game associated with the ID.
     * @throws EntityNotFoundException if the game is not found.
     */
    private Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }
}
