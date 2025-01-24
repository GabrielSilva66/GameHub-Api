/**
 * ValidCoupon
 *
 * <p>
 * Custom validation annotation used to ensure the validity of a coupon. It validates several conditions based on the coupon's 
 * type (percentage or value), its value, and the relationship between its initial and deadline dates.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCouponImpl.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoupon {

   /**
    * The default error message when the coupon is invalid.
    *
    * @return the default message.
    */
   String message() default "Invalid coupon.";

   /**
    * Groups for the validation constraints.
    *
    * @return the default groups.
    */
   Class<?>[] groups() default {};

   /**
    * Additional data to be carried with the annotation.
    *
    * @return the default payload.
    */
   Class<? extends Payload>[] payload() default {};
}
