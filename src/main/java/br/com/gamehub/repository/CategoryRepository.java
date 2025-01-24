/**
 * CategoryRepository
 *
 * <p>
 * Repository interface for handling data access for the Category entity.
 * Provides methods for searching and querying categories from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   
   /**
    * Searches for categories by name.
    * 
    * <p>
    * This method performs a case-insensitive search for categories whose names
    * match the given partial name. The results are paginated and returned as a {@link Page}.
    * </p>
    *
    * @param name the partial name of the category to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link Category} entities.
    */
   @NativeQuery("SELECT * FROM GH_CATEGORY WHERE no_name ILIKE %?1%")
   Page<Category> search(String name, Pageable pageable);
}
