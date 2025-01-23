/**
 * MaxDate
 *
 * <p>
 * Custom validation annotation to ensure that a date field is less than or equal to a specified maximum date.
 * This annotation can be used to validate date fields in models, ensuring that the release date or other date-related fields are not greater than a specified maximum date.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = MaxDateImpl.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxDate {

   /**
    * Error message to be returned when validation fails.
    *
    * @return the default error message.
    */
   String message() default "Release date must be less than or equal to the current date";

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

   /**
    * Maximum allowed date for the validation.
    * 
    * @return the maximum date in 'yyyy-MM-dd' format.
    */
   String maxDate() default "";
}
