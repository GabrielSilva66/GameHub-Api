package br.com.gamehub.repository;

import br.com.gamehub.model.Platform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

   // Usando a anotação @Query com nativeQuery = true
   @Query(value = "SELECT * FROM GH_PLATFORM WHERE no_name ILIKE %?1%", nativeQuery = true)
   Page<Platform> search(String name, Pageable pageable);
}
