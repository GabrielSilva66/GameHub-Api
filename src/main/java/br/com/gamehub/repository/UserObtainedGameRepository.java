package br.com.gamehub.repository;

import br.com.gamehub.model.UserObtainedGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserObtainedGameRepository extends JpaRepository<UserObtainedGame, Long> {
//    UserObtainedGame findByUserIdAndGameId(Long userId, Long gameId);
    Optional<UserObtainedGame> findByUserIdAndGameId(Long userId, Long gameId);

    List<UserObtainedGame> findAllByUserId(Long userId);

    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}
