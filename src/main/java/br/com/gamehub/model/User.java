/**
 * User
 *
 * <p>
 * Represents a user in the system. This entity contains information about the
 * user's email, password, user type, associated profile, and timestamps for creation and updates.
 * </p>
 *
 * <ul>
 * <li><strong>id:</strong> Unique identifier for the user.</li>
 * <li><strong>email:</strong> The user's email address, which must be unique and is required.</li>
 * <li><strong>passwordHash:</strong> The hashed password of the user.</li>
 * <li><strong>userType:</strong> The type of user, represented as an enum ({@link UserType}).</li>
 * <li><strong>profile:</strong> The associated {@link Profile} entity containing personal details of the user.</li>
 * <li><strong>createdAt:</strong> The timestamp of when the user was created.</li>
 * <li><strong>updatedAt:</strong> The timestamp of when the user was last updated.</li>
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

import br.com.gamehub.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GH_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {

    /**
     * The unique identifier for the user. Automatically generated as the primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The user's email address. This value must be unique and cannot be null.
     */
    @Column(name = "no_email", nullable = false, unique = true)
    private String email;

    /**
     * The hashed password of the user. This value cannot be null.
     */
    @Column(name = "no_password_hash", nullable = false)
    private String passwordHash;

    /**
     * The type of user, represented as an enum value ({@link UserType}).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    /**
     * The associated {@link Profile} entity containing personal details of the user.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile profile;

    /**
     * The timestamp when the user was created. This value is automatically set by Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the user was last updated. This value is automatically updated by Hibernate.
     */
    @Column(name = "dt_updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
