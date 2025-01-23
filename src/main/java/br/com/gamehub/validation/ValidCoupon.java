package br.com.gamehub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCouponImpl.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoupon {
    String message() default "Invalid coupon.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
