package br.com.gamehub.dto.request;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;
import br.com.gamehub.validation.ValidCouponValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidCouponValue
public record DiscountCouponRequestDTO(
      @NotNull(message = "Store id is required") Long storeId,
      @NotBlank(message = "Coupon name is required") String name,
      @NotNull(message = "Coupon type is required") CouponType couponType,
      @NotNull(message = "Coupon value is required") Double value,
      LocalDateTime initialDate,
      LocalDateTime deadline,
      @Min(value = 0, message = "Minimum price to use must be greater than or equal to zero") Double minPriceToUse) {
}
