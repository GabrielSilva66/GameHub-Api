package br.com.gamehub.repository;

import br.com.gamehub.model.Game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

   // Usando a anotação correta @Query com nativeQuery = true
   @Query(value = "SELECT * FROM GH_GAME WHERE no_name ILIKE %?1%", nativeQuery = true)
   Page<Game> search(String name, Pageable pageable);
}
