package br.com.gamehub.service;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.UserObtainedGameResponseDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.exception.EntityAlreadyExistsException;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.UserMapper;
import br.com.gamehub.mapper.UserObtainedGameMapper;
import br.com.gamehub.model.*;
import br.com.gamehub.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        return UserObtainedGameMapper.toResponseDTO(obtainedGame);
    }


    public List<UserObtainedGameResponseDTO> getAllObtainedGames(Long userId) {
        // Busca os jogos obtidos pelo ID do usuário
        List<UserObtainedGame> obtainedGames = obtainedGameRepository.findAllByUserId(userId);

        if (obtainedGames.isEmpty()) {
            throw new EntityNotFoundException("No obtained games found for user with id: " + userId);
        }

        return obtainedGames.stream()
                .map(UserObtainedGameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserObtainedGameResponseDTO getObtainedGameById(Long userId, Long gameId) {
        // Busca o jogo obtido pelo usuário e ID do jogo
        UserObtainedGame obtainedGame = obtainedGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Obtained game not found for user with id: " + userId + " and game id: " + gameId));

        return UserObtainedGameMapper.toResponseDTO(obtainedGame);
    }

    public void deleteObtainedGame(Long userId, Long gameId) {
        UserObtainedGame obtainedGame = obtainedGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("Obtained game not found for user with id: " + userId + " and game id: " + gameId));

        obtainedGameRepository.delete(obtainedGame);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + id));
    }

    private Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }
}
