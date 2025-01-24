/**
 * ActiveDiscountCoupon
 * 
 * <p>
 * Represents an active discount coupon that is applied to a game. This entity
 * is associated with a {@link DiscountCoupon} entity, and it stores information
 * about when the coupon was created and updated.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */

package br.com.gamehub.model;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "GH_ACTIVE_DISCOUNT_COUPON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ActiveDiscountCoupon {

   /**
    * The ID of the discount coupon. This is the primary key of this entity and is
    * embedded from the {@link DiscountCoupon} entity.
    */
   @EmbeddedId
   @EqualsAndHashCode.Include
   private Long idDiscountCoupon;

   /**
    * The associated {@link DiscountCoupon} entity that provides the details of the
    * discount.
    */
   @OneToOne(fetch = FetchType.LAZY)
   @MapsId
   @JoinColumn(name = "id_discount_coupon", nullable = false)
   private DiscountCoupon discountCoupon;

   /**
    * The timestamp when the active discount coupon was created. This value is set
    * automatically before persist.
    */
   @Column(name = "dt_created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
   private LocalDateTime createdAt;

   /**
    * The timestamp when the active discount coupon was last updated. This value is
    * updated automatically before an update operation.
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

   /**
    * Applies the discount from the associated {@link DiscountCoupon} to the given
    * game price.
    * 
    * <p>
    * If the game's price meets the minimum price requirement for the discount, the
    * discount is applied based on the coupon type:
    * </p>
    * <ul>
    * <li><strong>Percentage:</strong> The discount is applied as a percentage of
    * the game price.</li>
    * <li><strong>Value:</strong> The discount is applied as a fixed value
    * subtracted from the game price.</li>
    * </ul>
    * 
    * @param gamePrice The original price of the game.
    * @return The final price of the game after the discount has been applied.
    */
   Double applyDiscount(Double gamePrice) {
      if (this.discountCoupon.getMinPriceToUse() == null || this.discountCoupon.getMinPriceToUse() <= gamePrice) {
         if (this.discountCoupon.getCouponType() == CouponType.PERCENTAGE) {
            return gamePrice * (1 - this.discountCoupon.getValue());
         } else {
            return gamePrice - this.discountCoupon.getValue();
         }
      }

      return gamePrice;
   }
}
