package br.com.gamehub.dto.response;

import java.time.LocalDate;

public record ProfileResponseDTO(
        Long id,
        String name,
        LocalDate birthDate,
        String gender

) {}
