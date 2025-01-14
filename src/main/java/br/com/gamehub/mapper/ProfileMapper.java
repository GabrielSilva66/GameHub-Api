package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.ProfileResponseDTO;
import br.com.gamehub.enums.Gender;
import br.com.gamehub.model.Profile;
import br.com.gamehub.util.Converter;

import java.time.LocalDateTime;

public class ProfileMapper {

    // Converte ProfileRequestDTO para Profile (entidade)
    public static Profile toEntity(ProfileRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Profile profile = new Profile();
        profile.setGender(Converter.stringToEnum(Gender.class, dto.gender()));
        profile.setName(dto.name());
        profile.setBirthDate(dto.birthDate());

        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        return profile;
    }

    // Converte Profile (entidade) para ProfileResponseDTO
    public static ProfileResponseDTO toResponseDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        return new ProfileResponseDTO(
                profile.getId(),
                profile.getName(),
                profile.getBirthDate(),
                profile.getGender().toString()
        );
    }
}
