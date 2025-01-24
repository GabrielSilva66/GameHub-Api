/**
 * DeveloperRepository
 *
 * <p>
 * Repository interface for handling data access for the Developer entity.
 * Provides methods for searching and querying developers from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

   /**
    * Searches for developers by name.
    * 
    * <p>
    * This method performs a case-insensitive search for developers whose names
    * match the given partial name. The results are paginated and returned as a {@link Page}.
    * </p>
    *
    * @param name the partial name of the developer to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link Developer} entities.
    */
   @NativeQuery("SELECT * FROM GH_DEVELOPER WHERE no_name ILIKE %?1%")
   Page<Developer> search(String name, Pageable pageable);
}
