package br.com.gamehub.model;

import br.com.gamehub.enums.CouponType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "GH_DISCOUNT_COUPON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount_coupon")
    @EqualsAndHashCode.Include
    private Long idDiscountCoupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @Column(name = "no_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_coupon", nullable = false)
    private CouponType couponType;

    @Column(name = "nu_value", nullable = false)
    private Double value;

    @Column(name = "dt_deadline")
    private LocalDate deadline;

    @Column(name = "nu_maximum_use")
    private Integer maxUsage;

    @Column(name = "nu_minimum_price")
    private Double minPrice;

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
