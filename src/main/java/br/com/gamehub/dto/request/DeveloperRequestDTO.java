package br.com.gamehub.dto.request;

import br.com.gamehub.validation.MaxYear;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DeveloperRequestDTO(
      @NotBlank(message = "Name is mandatory") String name,
      @Min(value = 1900, message = "Year of foundation must be greater than 1900") @MaxYear Integer yearOfFoundation,
      String hostCountry) {
}
