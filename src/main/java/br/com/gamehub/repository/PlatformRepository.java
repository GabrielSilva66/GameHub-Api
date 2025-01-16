package br.com.gamehub.repository;

import br.com.gamehub.model.Platform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
   @NativeQuery("SELECT * FROM GH_PLATFORM WHERE no_name ILIKE %?1%")
   Page<Platform> search(String name, Pageable pageable);
}
