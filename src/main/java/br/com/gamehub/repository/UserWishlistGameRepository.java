package br.com.gamehub.repository;

import br.com.gamehub.model.UserWishlistGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWishlistGameRepository extends JpaRepository<UserWishlistGame, Long> {
}
