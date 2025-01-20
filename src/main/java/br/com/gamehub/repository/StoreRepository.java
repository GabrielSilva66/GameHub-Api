package br.com.gamehub.repository;

import br.com.gamehub.model.Store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface StoreRepository extends JpaRepository<Store, Long> {
   @NativeQuery("SELECT * FROM GH_STORE WHERE no_name ILIKE %?1%")
   Page<Store> search(String name, Pageable pageable);
}
