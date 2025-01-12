package br.com.gamehub.repository;

import br.com.gamehub.model.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {
}
