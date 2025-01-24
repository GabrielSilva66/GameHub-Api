/**
 * ValidCouponImpl
 *
 * <p>
 * Implementation of the validation logic for the {@link ValidCoupon} annotation. This class checks various conditions such as:
 * 1. If the coupon type is percentage, the value must be between 0 and 1.
 * 2. If the coupon type is value, the value must be at least 0.
 * 3. The initial date must be before the deadline.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.validation;

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.enums.CouponType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCouponImpl implements ConstraintValidator<ValidCoupon, DiscountCouponRequestDTO> {

   /**
    * Validates the provided {@link DiscountCouponRequestDTO} to ensure that it satisfies certain conditions:
    * <ul>
    * <li>If the coupon type is percentage, the value must be between 0 and 1.</li>
    * <li>If the coupon type is value, the value must be at least 0.</li>
    * <li>The initial date must be before the deadline.</li>
    * </ul>
    *
    * @param dto     the {@link DiscountCouponRequestDTO} to be validated.
    * @param context the context in which the validation is being performed.
    * @return true if all validations pass; false otherwise.
    */
   @Override
   public boolean isValid(DiscountCouponRequestDTO dto, ConstraintValidatorContext context) {
      if (dto.value() == null || dto.couponType() == null) {
         return true;
      }

      // Validation for percentage coupon
      if (dto.couponType().equals(CouponType.PERCENTAGE) && (dto.value() < 0 || dto.value() > 1)) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate("Percentage value must be between 0 and 1.")
               .addPropertyNode("value")
               .addConstraintViolation();
         return false;
      }

      // Validation for value coupon
      if (dto.couponType().equals(CouponType.VALUE) && dto.value() < 0) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate("Value must be at least 0 for non-percentage coupons.")
               .addPropertyNode("value")
               .addConstraintViolation();
         return false;
      }

      // Validation for date range
      if (dto.initialDate() != null && dto.deadline() != null && dto.initialDate().isAfter(dto.deadline())) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate("Initial date must be before deadline.")
               .addPropertyNode("initialDate")
               .addConstraintViolation();
         return false;
      }

      return true;
   }
}
