package br.com.gamehub.validation;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxYearImpl implements ConstraintValidator<MaxYear, Integer> {
   @Override
   public boolean isValid(Integer value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      int currentYear = Year.now().getValue();
      return value <= currentYear;
   }

}
