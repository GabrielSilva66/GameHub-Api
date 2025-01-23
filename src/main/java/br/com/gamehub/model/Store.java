/**
 * Store
 * 
 * <p>
 * Represents a store where games can be purchased or accessed. This entity
 * contains basic information about the store,
 * such as its name, URL, and timestamps for when the store was created and last
 * updated.
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
@Table(name = "GH_STORE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Store {

    /**
     * The unique identifier for the store. Automatically generated as the primary
     * key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_store")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The name of the store. This value cannot be null.
     */
    @Column(name = "no_name", nullable = false)
    private String name;

    /**
     * The URL of the store. This value cannot be null.
     */
    @Column(name = "no_url", nullable = false)
    private String url;

    /**
     * The timestamp when the store was created. This value is automatically set by
     * Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the store was last updated. This value is automatically
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
