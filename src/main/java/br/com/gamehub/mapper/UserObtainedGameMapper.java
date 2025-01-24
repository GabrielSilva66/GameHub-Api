/**
 * Utility class for mapping between {@link UserObtainedGameRequestDTO}, {@link UserObtainedGame} entity,
 * and {@link UserObtainedGameResponseDTO}.
 *
 * <p>
 * This class provides methods to convert a {@link UserObtainedGameRequestDTO} into a {@link UserObtainedGame} entity
 * and vice versa. It also maps a {@link UserObtainedGame} entity to a {@link UserObtainedGameResponseDTO} for returning responses.
 * </p>
 *
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.*;
import br.com.gamehub.enums.GameStatus;
import br.com.gamehub.model.*;
import br.com.gamehub.util.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class UserObtainedGameMapper {

    /**
     * Converts a {@link UserObtainedGameRequestDTO} to a {@link UserObtainedGame} entity.
     *
     * @param dto   The {@link UserObtainedGameRequestDTO} containing the data.
     * @param user  The {@link User} associated with the obtained game.
     * @param game  The {@link Game} associated with the obtained game.
     * @param store The {@link Store} where the game was obtained.
     * @return The corresponding {@link UserObtainedGame} entity.
     */
    public static UserObtainedGame toEntity(UserObtainedGameRequestDTO dto, User user, Game game, Store store) {
        UserObtainedGame obtainedGame = new UserObtainedGame();

        if (dto.statusGame() != null) {
            obtainedGame.setStatus(Converter.stringToEnum(GameStatus.class, dto.statusGame()));
        }

        // Setting other attributes
        obtainedGame.setUser(user);
        obtainedGame.setGame(game);
        obtainedGame.setStore(store);
        obtainedGame.setObtainedAt(dto.obtainedAt());

        return obtainedGame;
    }

    /**
     * Converts a {@link UserObtainedGame} entity to a {@link UserObtainedGameResponseDTO}.
     *
     * @param userObtainedGame The {@link UserObtainedGame} entity to be converted.
     * @return The corresponding {@link UserObtainedGameResponseDTO}.
     */
    public static UserObtainedGameResponseDTO toResponse(UserObtainedGame userObtainedGame) {
        return new UserObtainedGameResponseDTO(
                GameMapper.toResponse(userObtainedGame.getGame()),
                userObtainedGame.getStore().getName(),
                userObtainedGame.getStatus().toString(),
                userObtainedGame.getObtainedAt()
        );
    }
}
