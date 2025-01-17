package br.com.gamehub.repository;

import br.com.gamehub.model.Developer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

   // Usando a anotação correta @Query com nativeQuery = true
   @Query(value = "SELECT * FROM GH_DEVELOPER WHERE no_name ILIKE %?1%", nativeQuery = true)
   Page<Developer> search(String name, Pageable pageable);
}
