package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.*;
import br.com.gamehub.enums.GameStatus;
import br.com.gamehub.model.*;
import br.com.gamehub.util.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class UserObtainedGameMapper {

    public static UserObtainedGame toEntity(UserObtainedGameRequestDTO dto, User user, Game game, Store store) {
        UserObtainedGame obtainedGame = new UserObtainedGame();

        if (dto.statusGame() != null) {
            obtainedGame.setStatus(Converter.stringToEnum(GameStatus.class, dto.statusGame()));
        }

        // Configurando os demais atributos
        obtainedGame.setUser(user);
        obtainedGame.setGame(game);
        obtainedGame.setStore(store);
        obtainedGame.setObtainedAt(dto.obtainedAt());

        return obtainedGame;
    }

    public static UserObtainedGameResponseDTO toResponseDTO(UserObtainedGame userObtainedGame, GameResponseDTO gameResponseDTO){

        return new UserObtainedGameResponseDTO(gameResponseDTO,
                userObtainedGame.getStore().getName(),
                userObtainedGame.getStatus().toString(),
                userObtainedGame.getObtainedAt());
    }
}
