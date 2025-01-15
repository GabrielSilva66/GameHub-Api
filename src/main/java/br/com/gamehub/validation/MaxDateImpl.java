package br.com.gamehub.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxDateImpl implements ConstraintValidator<MaxDate, LocalDate> {
   private LocalDate maxDate;

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

   @Override
   public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      return value.compareTo(maxDate) <= 0;
   }
}