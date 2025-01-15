package br.com.gamehub.repository;

import br.com.gamehub.model.Developer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
   @NativeQuery("SELECT * FROM GH_DEVELOPER WHERE no_name ILIKE %?1%")
   Page<Developer> search(String name, Pageable pageable);
}
