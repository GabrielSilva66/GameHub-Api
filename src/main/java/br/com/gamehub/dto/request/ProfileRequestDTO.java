package br.com.gamehub.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProfileRequestDTO(
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        String name,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        String gender
) {}
