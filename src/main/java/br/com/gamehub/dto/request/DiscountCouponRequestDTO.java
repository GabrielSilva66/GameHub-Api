/**
 * DiscountCouponRequestDTO
 * 
 * <p>
 * Data Transfer Object for DiscountCoupon requests. This DTO is used for transferring discount coupon data when 
 * creating or updating a coupon associated with a store.
 * </p>
 * 
 * <p>
 * Validation rules for this DTO:
 * </p>
 * <ol>
 *     <li>Store ID is mandatory and cannot be null.</li>
 *     <li>Coupon name is mandatory and cannot be empty.</li>
 *     <li>Coupon type is mandatory and cannot be null.</li>
 *     <li>Coupon value is mandatory and cannot be null.</li>
 *     <li>The minimum price to use the coupon must be greater than or equal to zero.</li>
 * </ol>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.dto.request;

import java.time.LocalDateTime;

import br.com.gamehub.enums.CouponType;
import br.com.gamehub.validation.ValidCoupon;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a discount coupon request with necessary validation rules.
 * 
 * @param storeId       the ID of the store offering the coupon (cannot be
 *                      null).
 * @param name          the name of the coupon (cannot be empty).
 * @param couponType    the type of the coupon (cannot be null).
 * @param value         the value of the coupon (cannot be null).
 * @param initialDate   the starting date and time of the coupon's validity
 *                      (optional).
 * @param deadline      the expiry date and time of the coupon (optional).
 * @param minPriceToUse the minimum price required to use the coupon (must be >=
 *                      0).
 */
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
