package br.com.gamehub.mapper;

import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.dto.response.UserWishlistGameResponseDTO;
import br.com.gamehub.model.*;

public class UserWishlistGameMapper {
    public static UserWishlistGame toEntity(User user, Game game) {
        UserWishlistGame wishlistGame = new UserWishlistGame();
        wishlistGame.setUser(user);
        wishlistGame.setGame(game);

        return wishlistGame;
    }

    public static UserWishlistGameResponseDTO toResponseDTO(GameResponseDTO gameResponseDTO){
        return new UserWishlistGameResponseDTO(gameResponseDTO);
    }
}
