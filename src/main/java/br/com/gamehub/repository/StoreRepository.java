/**
 * StoreRepository
 *
 * <p>
 * Repository interface for handling data access for the Store entity.
 * Provides methods for querying stores from the database based on their names.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface StoreRepository extends JpaRepository<Store, Long> {

   /**
    * Searches for stores by name.
    * 
    * <p>
    * This method performs a case-insensitive search for stores whose names
    * match the given partial name. The results are paginated and returned as a {@link Page}.
    * </p>
    *
    * @param name the partial name of the store to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link Store} entities.
    */
   @NativeQuery("SELECT * FROM GH_STORE WHERE no_name ILIKE %?1%")
   Page<Store> search(String name, Pageable pageable);
}
