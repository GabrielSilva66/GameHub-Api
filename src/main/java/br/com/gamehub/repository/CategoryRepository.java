package br.com.gamehub.repository;

import br.com.gamehub.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

   // Usando @Query com uma query nativa do PostgreSQL
   @Query(value = "SELECT * FROM GH_CATEGORY WHERE no_name ILIKE %?1%", nativeQuery = true)
   Page<Category> search(String name, Pageable pageable);
}
