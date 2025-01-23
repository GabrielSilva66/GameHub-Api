package br.com.gamehub.repository;

import br.com.gamehub.id.StoreGameId;
import br.com.gamehub.model.StoreGame;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface StoreGameRepository extends JpaRepository<StoreGame, StoreGameId> {
   @NativeQuery("SELECT * FROM GH_STORE_GAME WHERE id_store = :storeId")
   List<StoreGame> findAllByStoreId(@Param("storeId") Long storeId);

   @NativeQuery("SELECT * FROM GH_STORE_GAME WHERE id_game = :gameId")
   List<StoreGame> findAllByGameId(@Param("gameId") Long gameId);

   @NativeQuery("""
         SELECT *
         FROM GH_STORE_GAME
         WHERE
            (:storeId IS NULL OR id_store = :storeId) AND
            (:gameId IS NULL OR id_game = :gameId) AND
            (:minPrice IS NULL OR price >= :minPrice) AND
            (:maxPrice IS NULL OR price <= :maxPrice)
         """)
   Page<StoreGame> search(@Param("storeId") Long storeId, @Param("gameId") Long gameId,
         @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);
}
