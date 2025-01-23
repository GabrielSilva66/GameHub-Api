package br.com.gamehub.validation;

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.enums.CouponType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCouponImpl implements ConstraintValidator<ValidCoupon, DiscountCouponRequestDTO> {

    @Override
    public boolean isValid(DiscountCouponRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.value() == null || dto.couponType() == null) {
            return true;
        }

        if (dto.couponType().equals(CouponType.PERCENTAGE) && (dto.value() < 0 || dto.value() > 1)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Percentage value must be between 0 and 1.")
                    .addPropertyNode("value")
                    .addConstraintViolation();
            return false;
        }

        if (dto.couponType().equals(CouponType.VALUE) && dto.value() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value must be at least 0 for non-percentage coupons.")
                    .addPropertyNode("value")
                    .addConstraintViolation();
            return false;
        }

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
