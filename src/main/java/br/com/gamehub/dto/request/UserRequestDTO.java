package br.com.gamehub.dto.request;

import br.com.gamehub.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is mandatory")
        String passwordHash,

        @NotNull(message = "User type is mandatory")
        UserType userType

) {}
