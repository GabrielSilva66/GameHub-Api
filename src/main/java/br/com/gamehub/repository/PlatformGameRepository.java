package br.com.gamehub.repository;

import br.com.gamehub.model.PlatformGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformGameRepository extends JpaRepository<PlatformGame, Long> {
}
