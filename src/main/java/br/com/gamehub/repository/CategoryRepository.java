package br.com.gamehub.repository;

import br.com.gamehub.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   @NativeQuery("SELECT * FROM GH_CATEGORY WHERE no_name ILIKE %?1%")
   Page<Category> search(String name, Pageable pageable);
}
