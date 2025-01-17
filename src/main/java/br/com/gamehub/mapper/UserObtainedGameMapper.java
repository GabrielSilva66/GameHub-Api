package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.UserObtainedGameResponseDTO;
import br.com.gamehub.enums.GameStatus;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.Store;
import br.com.gamehub.model.User;
import br.com.gamehub.model.UserObtainedGame;
import br.com.gamehub.util.Converter;

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

    public static UserObtainedGameResponseDTO toResponseDTO(UserObtainedGame userObtainedGame){
        return new UserObtainedGameResponseDTO(userObtainedGame.getGame(),
                userObtainedGame.getStore().getName(),
                userObtainedGame.getStatus().toString(),
                userObtainedGame.getObtainedAt());
    }
}
