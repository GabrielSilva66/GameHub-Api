package br.com.gamehub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCouponValueImpl.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCouponValue {
    String message() default "Invalid coupon value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
