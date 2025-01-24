/**
 * StoreGameRepository
 *
 * <p>
 * Repository interface for handling data access for the StoreGame entity.
 * Provides methods for querying StoreGame data based on store, game, and price information.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
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
      /**
       * Retrieves all StoreGame records by store ID.
       * 
       * <p>
       * This method retrieves all StoreGame entities that are associated with the
       * specified store ID.
       * </p>
       *
       * @param storeId the ID of the store.
       * @return a list of {@link StoreGame} entities associated with the given store
       *         ID.
       */
      @NativeQuery("SELECT * FROM GH_STORE_GAME WHERE id_store = :storeId")
      List<StoreGame> findAllByStoreId(@Param("storeId") Long storeId);

      /**
       * Retrieves all StoreGame records by game ID.
       * 
       * <p>
       * This method retrieves all StoreGame entities that are associated with the
       * specified game ID.
       * </p>
       *
       * @param gameId the ID of the game.
       * @return a list of {@link StoreGame} entities associated with the given game
       *         ID.
       */
      @NativeQuery("SELECT * FROM GH_STORE_GAME WHERE id_game = :gameId")
      List<StoreGame> findAllByGameId(@Param("gameId") Long gameId);

      /**
       * Searches for StoreGame records based on store ID, game ID, and price range.
       * 
       * <p>
       * This method performs a search for StoreGame entities that match the specified
       * store ID, game ID,
       * and/or price range. The results are paginated and returned as a {@link Page}.
       * </p>
       *
       * @param storeId  the ID of the store.
       * @param gameId   the ID of the game.
       * @param minPrice the minimum price for filtering results.
       * @param maxPrice the maximum price for filtering results.
       * @param pageable pagination information including page number and page size.
       * @return a {@link Page} containing the matching {@link StoreGame} entities.
       */
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
