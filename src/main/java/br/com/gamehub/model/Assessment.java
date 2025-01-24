/**
 * Assessment
 *
 * <p>
 * Represents a user assessment of a game in the system. This entity stores the
 * rating, optional comment, and timestamps for creation and updates. Each
 * assessment is linked to a specific {@link User} and {@link Game}.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> Unique identifier of the assessment.</li>
 * <li><strong>user:</strong> The user who provided the assessment.</li>
 * <li><strong>game:</strong> The game being evaluated.</li>
 * <li><strong>rating:</strong> Numeric rating between 1 and 10.</li>
 * <li><strong>comment:</strong> Optional textual comment (max 1024 characters).</li>
 * <li><strong>createdAt:</strong> Timestamp of when the assessment was created.</li>
 * <li><strong>updatedAt:</strong> Timestamp of when the assessment was last updated.</li>
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

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_ASSESSMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Assessment {

    /**
     * The unique identifier for the assessment. Automatically generated as the
     * primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assessment")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The user who created the assessment.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     * The game being assessed.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    /**
     * The numeric rating provided by the user, between 1 and 10.
     */
    @Column(name = "nu_rating", nullable = false)
    private Integer rating;

    /**
     * An optional comment provided by the user. Maximum length of 1024 characters.
     */
    @Column(name = "ds_comment", length = 1024)
    private String comment;

    /**
     * The timestamp when the assessment was created. Automatically set on persist.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the assessment was last updated. Automatically updated on modification.
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
