package br.com.gamehub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
                @NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,

                @NotBlank(message = "Password is mandatory") String password) {
}
