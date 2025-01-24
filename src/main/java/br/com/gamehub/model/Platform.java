/**
 * Platform
 * 
 * <p>
 * Represents a platform on which games can be played. This entity contains
 * information such as the platform's name
 * and timestamps for when the platform was created and last updated.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_PLATFORM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Platform {

    /**
     * The unique identifier for the platform. Automatically generated as the
     * primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_platform")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The name of the platform. This value is unique and cannot be null.
     */
    @Column(name = "no_name", nullable = false, unique = true)
    private String name;

    /**
     * The timestamp when the platform was created. This value is automatically set
     * by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the platform was last updated. This value is automatically
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
