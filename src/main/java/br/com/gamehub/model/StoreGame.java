package br.com.gamehub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import br.com.gamehub.id.StoreGameId;

@Entity
@Table(name = "GH_STORE_GAME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class StoreGame {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private StoreGameId storeGameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeId")
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    @Column(name = "nu_price", nullable = false)
    private Double price;

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
