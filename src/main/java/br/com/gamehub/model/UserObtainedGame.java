/**
 * UserObtainedGame
 *
 * <p>
 * Represents a record of a game obtained by a user in the system. This entity
 * stores the associations between the user, game, store, and relevant details such as the obtained date,
 * game status, and timestamps for creation and updates.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> Unique identifier for the record.</li>
 * <li><strong>user:</strong> The user who obtained the game, represented by the {@link User} entity.</li>
 * <li><strong>game:</strong> The game obtained, represented by the {@link Game} entity.</li>
 * <li><strong>store:</strong> The store where the game was obtained, represented by the {@link Store} entity.</li>
 * <li><strong>obtainedAt:</strong> The date when the game was obtained.</li>
 * <li><strong>status:</strong> The status of the game (e.g., played, not_played), represented as an enum ({@link GameStatus}).</li>
 * <li><strong>createdAt:</strong> The timestamp of when the record was created.</li>
 * <li><strong>updatedAt:</strong> The timestamp of when the record was last updated.</li>
 * </ul>
 *
 * <p>
 * This entity uses JPA lifecycle hooks to automatically manage the
 * {@code createdAt} and {@code updatedAt} timestamps.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import br.com.gamehub.enums.GameStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_USER_OBTAINED")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserObtainedGame {

    /**
     * The unique identifier for the record. Automatically generated as the primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_obtained")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The user who obtained the game. Mapped to the {@link User} entity.
     */
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     * The game obtained by the user. Mapped to the {@link Game} entity.
     */
    @ManyToOne
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    /**
     * The store where the game was obtained. Mapped to the {@link Store} entity.
     */
    @ManyToOne
    @JoinColumn(name = "id_store")
    private Store store;

    /**
     * The date when the game was obtained by the user.
     */
    @Column(name = "dt_obtained_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDate obtainedAt;

    /**
     * The status of the game (e.g., played, not_played), represented as an enum value ({@link GameStatus}).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'not_played'")
    private GameStatus status;

    /**
     * The timestamp when the record was created. Automatically set by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the record was last updated. Automatically updated by Hibernate.
     */
    @Column(name = "dt_updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
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
