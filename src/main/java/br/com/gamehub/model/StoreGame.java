/**
 * StoreGame
 * 
 * <p>
 * Represents the relationship between a store and a game. This entity stores the price of a game in a particular store
 * and tracks when this information was created and last updated. It uses a composite primary key formed by the combination
 * of store and game identifiers.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import br.com.gamehub.id.StoreGameId;

@Entity
@Table(name = "GH_STORE_GAME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class StoreGame {

    /**
     * The composite primary key formed by the store and game identifiers.
     */
    @EmbeddedId
    @EqualsAndHashCode.Include
    private StoreGameId storeGameId;

    /**
     * The store where the game is available. This is a many-to-one relationship
     * with the Store entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeId")
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    /**
     * The game available in the store. This is a many-to-one relationship with the
     * Game entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    /**
     * The price of the game in the store. This value cannot be null.
     */
    @Column(name = "nu_price", nullable = false)
    private Double price;

    /**
     * The timestamp when the store-game relationship was created. This value is
     * automatically set by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the store-game relationship was last updated. This value
     * is automatically updated by Hibernate.
     */
    @Column(name = "dt_updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;

    /**
     * Sets the {@link #createdAt} and {@link #updatedAt} timestamps to the current
     * time before the entity is persisted.
     */
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the {@link #updatedAt} timestamp to the current time before the
     * entity is updated.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
