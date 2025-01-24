/**
 * Game
 *
 * <p>
 * Represents a game in the system. This entity contains information about the
 * game, such as its name, release date, associated developer, categories,
 * platforms, ratings, and timestamps for creation and updates.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> Unique identifier for the game.</li>
 * <li><strong>developer:</strong> The developer of the game, mapped to the {@link Developer} entity.</li>
 * <li><strong>name:</strong> The name of the game, which cannot be null.</li>
 * <li><strong>releaseDate:</strong> The release date of the game.</li>
 * <li><strong>categories:</strong> A list of categories the game belongs to, mapped to the {@link Category} entity.</li>
 * <li><strong>platforms:</strong> A list of platforms the game is available on, mapped to the {@link Platform} entity.</li>
 * <li><strong>rating:</strong> The average rating of the game, defaulting to 0.0.</li>
 * <li><strong>totalEvaluation:</strong> The total number of evaluations, defaulting to 0.</li>
 * <li><strong>createdAt:</strong> The timestamp of when the game was created.</li>
 * <li><strong>updatedAt:</strong> The timestamp of when the game was last updated.</li>
 * </ul>
 *
 * <p>
 * This entity uses JPA lifecycle hooks to automatically manage the
 * {@code createdAt} and {@code updatedAt} timestamps.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GH_GAME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Game {

    /**
     * The unique identifier for the game. Automatically generated as the primary
     * key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The developer of the game. This field is mapped to the {@link Developer}
     * entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_developer", nullable = false)
    private Developer developer;

    /**
     * The name of the game. This value cannot be null.
     */
    @Column(name = "no_name", nullable = false)
    private String name;

    /**
     * The release date of the game.
     */
    @Column(name = "dt_release")
    private LocalDate releaseDate;

    /**
     * A list of categories the game belongs to. This is a many-to-many relationship
     * with the {@link Category} entity.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GH_GAME_CATEGORY",
            joinColumns = @JoinColumn(name = "id_game"),
            inverseJoinColumns = @JoinColumn(name = "id_category"))
    private List<Category> categories;

    /**
     * A list of platforms the game is available on. This is a many-to-many
     * relationship with the {@link Platform} entity.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GH_GAME_PLATFORM",
            joinColumns = @JoinColumn(name = "id_game"),
            inverseJoinColumns = @JoinColumn(name = "id_platform"))
    private List<Platform> platforms;

    /**
     * The average rating of the game. Default value is 0.0.
     */
    @Column(name = "nu_rating")
    private Double rating = 0.0;

    /**
     * The total number of evaluations for the game. Default value is 0.
     */
    @Column(name = "nu_total_evaluation", columnDefinition = "integer default 0")
    private Integer totalEvaluation = 0;

    /**
     * The timestamp when the game was created. This value is automatically set by
     * Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the game was last updated. This value is automatically
     * updated by Hibernate.
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
