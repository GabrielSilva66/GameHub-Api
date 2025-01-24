/**
 * MaxYearImpl
 *
 * <p>
 * The implementation of the custom validation logic for the {@link MaxYear} annotation. This class checks if the provided year 
 * is less than or equal to the current year.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.validation;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxYearImpl implements ConstraintValidator<MaxYear, Integer> {

   /**
    * Validates that the provided year is less than or equal to the current year.
    *
    * @param value   the year to be validated.
    * @param context the context in which the constraint is being validated.
    * @return true if the year is valid (less than or equal to the current year), false otherwise.
    */
   @Override
   public boolean isValid(Integer value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      int currentYear = Year.now().getValue();
      return value <= currentYear;
   }
}
