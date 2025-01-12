package br.com.gamehub.repository;

import br.com.gamehub.model.UserObtainedGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserObtainedGameRepository extends JpaRepository<UserObtainedGame, Long> {
}
