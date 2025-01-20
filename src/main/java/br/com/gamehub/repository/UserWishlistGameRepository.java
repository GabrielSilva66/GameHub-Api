package br.com.gamehub.repository;

import br.com.gamehub.model.UserWishlistGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWishlistGameRepository extends JpaRepository<UserWishlistGame, Long> {
    Optional<UserWishlistGame> findByUserIdAndGameId(Long userId, Long gameId);

    List<UserWishlistGame> findAllByUserId(Long userId);
}
