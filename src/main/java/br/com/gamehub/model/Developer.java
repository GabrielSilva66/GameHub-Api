/**
 * Developer
 * 
 * <p>
 * Represents a developer of games in the system. This entity stores the name, year of foundation, host country, 
 * and timestamps for when the developer was created and last updated.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GH_DEVELOPER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Developer {

    /**
     * The unique identifier for the developer. Automatically generated as the
     * primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_developer")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The name of the developer. This value cannot be null.
     */
    @Column(name = "no_name", nullable = false)
    private String name;

    /**
     * The year the developer was founded.
     */
    @Column(name = "nu_year_of_foundation")
    private Integer yearOfFoundation;

    /**
     * The country where the developer is based.
     */
    @Column(name = "no_host_country")
    private String hostCountry;

    /**
     * The timestamp when the developer was created. This value is automatically set
     * by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the developer was last updated. This value is
     * automatically updated by Hibernate.
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
