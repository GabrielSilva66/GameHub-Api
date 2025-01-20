package br.com.gamehub.validation;

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCouponValueImpl implements ConstraintValidator<ValidCouponValue, DiscountCouponRequestDTO> {

    @Override
    public boolean isValid(DiscountCouponRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.value() == null || dto.couponType() == null) {
            return true;
        }

        if (dto.couponType().equals("PERCENTAGE") && (dto.value() < 0 || dto.value() > 1)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Percentage value must be between 0 and 1.")
                    .addPropertyNode("value")
                    .addConstraintViolation();
            return false;
        }

        if (!dto.couponType().equals("PERCENTAGE") && dto.value() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value must be at least 0 for non-percentage coupons.")
                    .addPropertyNode("value")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
