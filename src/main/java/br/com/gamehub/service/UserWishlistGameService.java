package br.com.gamehub.service;

import br.com.gamehub.dto.request.UserWishlistGameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
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
    private final GameService gameService;

    @Autowired
    public UserWishlistGameService(UserRepository userRepository, UserObtainedGameRepository obtainedGameRepository,
                                   UserWishlistGameRepository wishlistGameRepository, GameRepository gameRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.wishlistGameRepository = wishlistGameRepository;
        this.obtainedGameRepository = obtainedGameRepository;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    /**
     * Adiciona um jogo à lista de desejos do usuário.
     */
    public UserWishlistGameResponseDTO createWishlistGame(Long userId, UserWishlistGameRequestDTO wishlistGameDTO) {

        User user = findUserById(userId);
        Game game = findGameById(wishlistGameDTO.gameId());

        boolean alreadyObtained = obtainedGameRepository.findByUserIdAndGameId(userId, wishlistGameDTO.gameId()).isPresent();
        if (alreadyObtained) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already obtained the game with id " + wishlistGameDTO.gameId());
        }

        boolean alreadyWishlist = wishlistGameRepository.findByUserIdAndGameId(userId, wishlistGameDTO.gameId()).isPresent();
        if (alreadyWishlist) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already wishlisted the game with id " + wishlistGameDTO.gameId());
        }

        UserWishlistGame wishlistGame = UserWishlistGameMapper.toEntity(user, game);
        wishlistGame = wishlistGameRepository.save(wishlistGame);

        return UserWishlistGameMapper.toResponseDTO(gameService.getGameById(game.getId()));
    }

    /**
     * Retorna todos os jogos na lista de desejos do usuário.
     */
    public List<UserWishlistGameResponseDTO> getAllWishlistGames(Long userId) {
        List<UserWishlistGame> wishlistGames = wishlistGameRepository.findAllByUserId(userId);

        if (wishlistGames.isEmpty()) {
            throw new EntityNotFoundException("No wishlist games found for user with id: " + userId);
        }

        return wishlistGames.stream()
                .map(userWishlistGame -> {
                    GameResponseDTO gameResponseDTO = gameService.getGameById(userWishlistGame.getGame().getId());
                    return UserWishlistGameMapper.toResponseDTO(gameResponseDTO);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retorna detalhes de um jogo específico da lista de desejos do usuário.
     */
    public UserWishlistGameResponseDTO getWishlistGameById(Long userId, Long gameId) {
        UserWishlistGame wishlistGame = wishlistGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist game not found for user with id: " + userId + " and game id: " + gameId));

        return UserWishlistGameMapper.toResponseDTO(gameService.getGameById(gameId));
    }

    /**
     * Remove um jogo da lista de desejos do usuário.
     */
    public void deleteWishlistGame(Long userId, Long gameId) {
        UserWishlistGame wishlistGame = wishlistGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist game not found for user with id: " + userId + " and game id: " + gameId));

        wishlistGameRepository.delete(wishlistGame);
    }

    /**
     * Retorna um usuário pelo ID.
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Retorna um jogo pelo ID.
     */
    private Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }
}
