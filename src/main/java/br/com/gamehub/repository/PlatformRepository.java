/**
 * PlatformRepository
 *
 * <p>
 * Repository interface for handling data access for the Platform entity.
 * Provides methods for searching and querying platforms from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

   /**
    * Searches for platforms by name.
    * 
    * <p>
    * This method performs a case-insensitive search for platforms whose names
    * match the given partial name. The results are paginated and returned as a
    * {@link Page}.
    * </p>
    *
    * @param name     the partial name of the platform to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link Platform} entities.
    */
   @NativeQuery("SELECT * FROM GH_PLATFORM WHERE no_name ILIKE %?1%")
   Page<Platform> search(String name, Pageable pageable);
}
