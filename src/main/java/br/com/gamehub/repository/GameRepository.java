/**
 * GameRepository
 *
 * <p>
 * Repository interface for handling data access for the Game entity.
 * Provides methods for searching and querying games from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface GameRepository extends JpaRepository<Game, Long> {

   /**
    * Searches for games by name.
    * 
    * <p>
    * This method performs a case-insensitive search for games whose names
    * match the given partial name. The results are paginated and returned as a {@link Page}.
    * </p>
    *
    * @param name the partial name of the game to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link Game} entities.
    */
   @NativeQuery("SELECT * FROM GH_GAME WHERE no_name ILIKE %?1%")
   Page<Game> search(String name, Pageable pageable);
}
