package br.com.gamehub.dto.response;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;

public record DiscountCouponResponseDTO(
      Long id,
      String name,
      CouponType couponType,
      Double value,
      LocalDateTime initialDate,
      LocalDateTime deadline,
      Double minPriceToUse) {
}
