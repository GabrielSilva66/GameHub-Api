/**
 * DiscountCouponResponseDTO
 * 
 * <p>
 * Data Transfer Object for DiscountCoupon response. This DTO is used for transferring discount coupon data in responses.
 * </p>
 * 
 * <p>
 * The DiscountCouponResponseDTO contains information about the discount coupon, including its ID, name, associated store, coupon type, value, 
 * validity dates, and minimum price to use.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.response;

import java.time.LocalDateTime;
import br.com.gamehub.enums.CouponType;

/**
 * Represents a discount coupon response with its ID, name, associated store, coupon type, value, validity dates, and minimum price to use.
 * 
 * @param id the unique identifier of the discount coupon.
 * @param name the name of the discount coupon.
 * @param store the store that is offering the coupon.
 * @param couponType the type of the coupon (e.g., percentage, fixed amount).
 * @param value the value of the discount coupon.
 * @param initialDate the start date of the coupon's validity.
 * @param deadline the expiration date of the coupon.
 * @param minPriceToUse the minimum price required to use the coupon.
 */
public record DiscountCouponResponseDTO(
            Long id,
            String name,
            StoreResponseDTO store,
            CouponType couponType,
            Double value,
            LocalDateTime initialDate,
            LocalDateTime deadline,
            Double minPriceToUse) {
}
