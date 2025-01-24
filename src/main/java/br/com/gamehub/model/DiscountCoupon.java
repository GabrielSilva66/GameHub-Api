/**
 * DiscountCoupon
 * 
 * <p>
 * Represents a discount coupon in the system. This entity stores information
 * about the coupon such as its name,
 * type, value, associated store, and relevant dates including its validity
 * period and timestamps for creation and update.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import br.com.gamehub.enums.CouponType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GH_DISCOUNT_COUPON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class DiscountCoupon {

    /**
     * The unique identifier for the discount coupon. Automatically generated as the
     * primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount_coupon")
    @EqualsAndHashCode.Include
    private Long idDiscountCoupon;

    /**
     * The store associated with the discount coupon. This field is mapped to the
     * {@link Store} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    /**
     * The name of the discount coupon. This value cannot be null.
     */
    @Column(name = "no_name", nullable = false)
    private String name;

    /**
     * The type of the coupon, which can either be a fixed value or a percentage.
     * This is represented by the {@link CouponType} enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_coupon", nullable = false)
    private CouponType couponType;

    /**
     * The value of the discount coupon, either as a fixed amount or as a
     * percentage.
     */
    @Column(name = "nu_value", nullable = false)
    private Double value;

    /**
     * The start date of the coupon's validity.
     */
    @Column(name = "dt_initial")
    private LocalDateTime initialDate;

    /**
     * The end date of the coupon's validity.
     */
    @Column(name = "dt_deadline")
    private LocalDateTime deadline;

    /**
     * The minimum price required to apply the discount coupon.
     */
    @Column(name = "nu_minimum_price")
    private Double minPriceToUse;

    /**
     * The timestamp when the coupon was created. This value is automatically set by
     * Hibernate.
     */
    @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the coupon was last updated. This value is automatically
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
