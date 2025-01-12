package br.com.gamehub.repository;

import br.com.gamehub.model.StoreGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreGameRepository extends JpaRepository<StoreGame, Long> {
}
