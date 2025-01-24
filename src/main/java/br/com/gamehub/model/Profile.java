/**
 * Profile
 *
 * <p>
 * Represents a user profile in the system. This entity contains personal
 * details such as the name, gender, birth date, and timestamps for creation
 * and update.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> Unique identifier for the profile.</li>
 * <li><strong>birthDate:</strong> The birth date of the profile owner.</li>
 * <li><strong>name:</strong> The full name of the profile owner.</li>
 * <li><strong>gender:</strong> The gender of the profile owner, represented as
 * an enum value ({@link Gender}).</li>
 * <li><strong>createdAt:</strong> The timestamp of when the profile was
 * created.</li>
 * <li><strong>updatedAt:</strong> The timestamp of when the profile was last
 * updated.</li>
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

import br.com.gamehub.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_PROFILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Profile {

    /**
     * The unique identifier for the profile. Automatically generated as the primary
     * key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The birth date of the profile owner.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * The name of the profile owner.
     */
    @Column(name = "no_name", nullable = false)
    private String name;

    /**
     * The gender of the profile owner, represented as an enum value.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * The timestamp when the profile was created. This value is automatically set
     * by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the profile was last updated. This value is automatically
     * updated by Hibernate.
     */
    @Column(name = "dt_updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
