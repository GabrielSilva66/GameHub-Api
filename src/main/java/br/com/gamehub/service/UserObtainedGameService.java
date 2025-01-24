/**
 * Service for managing user obtained games.
 * <p>
 * This service provides methods to create, retrieve, and delete user obtained games.
 * It interacts with the {@link UserObtainedGameRepository}, {@link UserRepository}, {@link StoreRepository}, and {@link GameRepository}.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.UserObtainedGameResponseDTO;
import br.com.gamehub.exception.EntityAlreadyExistsException;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.UserObtainedGameMapper;
import br.com.gamehub.model.*;
import br.com.gamehub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserObtainedGameService {

    private final UserRepository userRepository;
    private final UserObtainedGameRepository obtainedGameRepository;
    private final StoreRepository storeRepository;
    private final GameRepository gameRepository;

    @Autowired
    public UserObtainedGameService(UserRepository userRepository, UserObtainedGameRepository obtainedGameRepository,
                                   StoreRepository storeRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.obtainedGameRepository = obtainedGameRepository;
        this.storeRepository = storeRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Creates a new obtained game for a user.
     *
     * @param userId the ID of the user.
     * @param obtainedGameDTO the request DTO for the obtained game.
     * @return the response DTO for the newly created obtained game.
     * @throws EntityAlreadyExistsException if the user has already obtained the game.
     */
    public UserObtainedGameResponseDTO createObtainedGame(Long userId, UserObtainedGameRequestDTO obtainedGameDTO) {
        User user = findUserById(userId);
        Store store = findStoreById(obtainedGameDTO.id_store());
        Game game = findGameById(obtainedGameDTO.id_game());

        boolean alreadyObtained = obtainedGameRepository.findByUserIdAndGameId(userId, obtainedGameDTO.id_game()).isPresent();
        if (alreadyObtained) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already obtained the game with id " + obtainedGameDTO.id_game());
        }

        UserObtainedGame obtainedGame = UserObtainedGameMapper.toEntity(obtainedGameDTO, user, game, store);
        obtainedGame = obtainedGameRepository.save(obtainedGame);

        return UserObtainedGameMapper.toResponse(obtainedGame);
    }

    /**
     * Retrieves all obtained games for a user.
     *
     * @param userId the ID of the user.
     * @return a list of response DTOs for the obtained games.
     * @throws EntityNotFoundException if no obtained games are found for the user.
     */
    public List<UserObtainedGameResponseDTO> getAllObtainedGames(Long userId) {
        List<UserObtainedGame> obtainedGames = obtainedGameRepository.findAllByUserId(userId);

        if (obtainedGames.isEmpty()) {
            throw new EntityNotFoundException("No obtained games found for user with id: " + userId);
        }

        return obtainedGames.stream()
                .map(UserObtainedGameMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific obtained game by user ID and game ID.
     *
     * @param userId the ID of the user.
     * @param gameId the ID of the game.
     * @return the response DTO for the obtained game.
     * @throws EntityNotFoundException if the obtained game is not found.
     */
    public UserObtainedGameResponseDTO getObtainedGameById(Long userId, Long gameId) {
        UserObtainedGame obtainedGame = obtainedGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Obtained game not found for user with id: " + userId + " and game id: " + gameId));

        return UserObtainedGameMapper.toResponse(obtainedGame);
    }

    /**
     * Deletes an obtained game for a user by user ID and game ID.
     *
     * @param userId the ID of the user.
     * @param gameId the ID of the game.
     * @throws EntityNotFoundException if the obtained game is not found.
     */
    public void deleteObtainedGame(Long userId, Long gameId) {
        UserObtainedGame obtainedGame = obtainedGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Obtained game not found for user with id: " + userId + " and game id: " + gameId));

        obtainedGameRepository.delete(obtainedGame);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user.
     * @return the user entity.
     * @throws EntityNotFoundException if the user is not found.
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Finds a store by its ID.
     *
     * @param id the ID of the store.
     * @return the store entity.
     * @throws EntityNotFoundException if the store is not found.
     */
    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + id));
    }

    /**
     * Finds a game by its ID.
     *
     * @param id the ID of the game.
     * @return the game entity.
     * @throws EntityNotFoundException if the game is not found.
     */
    private Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }
}
