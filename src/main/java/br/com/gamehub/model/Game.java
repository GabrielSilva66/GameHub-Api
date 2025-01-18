package br.com.gamehub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "GH_GAME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_developer", nullable = false)
    private Developer developer;

    @Column(name = "no_name", nullable = false)
    private String name;

    @Column(name = "dt_release")
    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GH_GAME_CATEGORY", joinColumns = @JoinColumn(name = "id_game"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GH_GAME_PLATFORM", joinColumns = @JoinColumn(name = "id_game"), inverseJoinColumns = @JoinColumn(name = "id_platform"))
    private List<Platform> platforms;

    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "dt_updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
