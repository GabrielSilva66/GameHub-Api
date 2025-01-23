package br.com.gamehub.dto.request;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;
import br.com.gamehub.validation.ValidCoupon;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidCoupon
public record DiscountCouponRequestDTO(
      @NotNull(message = "Store id cannot be null") Long storeId,
      @NotBlank(message = "Coupon name is mandatory") String name,
      @NotNull(message = "Coupon type cannot be null") CouponType couponType,
      @NotNull(message = "Coupon value cannot be null") Double value,
      LocalDateTime initialDate,
      LocalDateTime deadline,
      @Min(value = 0, message = "Minimum price to use must be greater than or equal to zero") Double minPriceToUse) {
}
