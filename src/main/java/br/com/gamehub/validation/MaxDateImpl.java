/**
 * MaxDateImpl
 *
 * <p>
 * The implementation of the custom validation logic for the {@link MaxDate} annotation. This class checks if the provided date 
 * is less than or equal to the specified maximum date. The maximum date can either be passed explicitly or defaults to the current date.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxDateImpl implements ConstraintValidator<MaxDate, LocalDate> {
   private LocalDate maxDate;

   /**
    * Initializes the validator with the provided {@link MaxDate} annotation.
    * If no specific maximum date is provided, the current date is used as the default maximum.
    *
    * @param constraintAnnotation the annotation that defines the validation logic.
    */
   @Override
   public void initialize(MaxDate constraintAnnotation) {
      String maxDateStr = constraintAnnotation.maxDate();
      
      if (!maxDateStr.isEmpty()) {
         try {
            this.maxDate = LocalDate.parse(maxDateStr);
         } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid maxDate format. Use 'yyyy-MM-dd'.", e);
         }
      } else {
         this.maxDate = LocalDate.now();
      }
   }

   /**
    * Validates that the provided date is less than or equal to the maximum date.
    *
    * @param value   the date to be validated.
    * @param context the context in which the constraint is being validated.
    * @return true if the date is valid (less than or equal to the maximum date), false otherwise.
    */
   @Override
   public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      return value.compareTo(maxDate) <= 0;
   }
}
