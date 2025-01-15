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
   String message() default "Release date must be less than or equal to the current date";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

   String maxDate() default "";
}
