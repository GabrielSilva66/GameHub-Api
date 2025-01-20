package br.com.gamehub.model;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
   @Id
   @Column(name = "id_discount_coupon")
   @EqualsAndHashCode.Include
   private Long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_discount_coupon", nullable = false)
   private DiscountCoupon discountCoupon;

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
