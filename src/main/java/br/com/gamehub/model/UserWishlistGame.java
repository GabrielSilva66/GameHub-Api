/**
 * UserWishlistGame
 *
 * <p>
 * Represents a game that a user has added to their wishlist in the system. This entity
 * stores the relationship between the user and the game, as well as timestamps for when the record
 * was created and last updated.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> The unique identifier for the record, automatically generated as the primary key of the entity.</li>
 * <li><strong>user:</strong> The user who added the game to their wishlist, represented by the {@link User} entity.</li>
 * <li><strong>game:</strong> The game added to the user's wishlist, represented by the {@link Game} entity.</li>
 * <li><strong>createdAt:</strong> The timestamp when the record was created, automatically set by Hibernate.</li>
 * <li><strong>updatedAt:</strong> The timestamp when the record was last updated, automatically updated by Hibernate.</li>
 * </ul>
 *
 * <p>
 * This entity uses JPA lifecycle hooks to automatically manage the {@code createdAt} and {@code updatedAt} timestamps.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GH_USER_WISHLIST_GAME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserWishlistGame {

    /**
     * The unique identifier for the record. Automatically generated as the primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_wishlist_game")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The user who added the game to their wishlist. Mapped to the {@link User} entity.
     */
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     * The game added to the user's wishlist. Mapped to the {@link Game} entity.
     */
    @ManyToOne
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    /**
     * The timestamp when the record was created. Automatically set by Hibernate.
     */
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the record was last updated. Automatically updated by Hibernate.
     */
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;

    /**
     * Sets the {@link #createdAt} and {@link #updatedAt} timestamps to the current time before the entity is persisted.
     */
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the {@link #updatedAt} timestamp to the current time before the entity is updated.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
