package br.com.gamehub.repository;

import br.com.gamehub.model.Game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface GameRepository extends JpaRepository<Game, Long> {
   @NativeQuery("SELECT * FROM GH_GAME WHERE no_name ILIKE %?1%")
   Page<Game> search(String name, Pageable pageable);
}
