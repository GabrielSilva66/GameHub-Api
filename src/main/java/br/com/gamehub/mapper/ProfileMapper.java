/**
 * Utility class for mapping between {@link ProfileRequestDTO}, {@link Profile} entity, and {@link ProfileResponseDTO}.
 *
 * <p>
 * This class provides methods to convert a {@link ProfileRequestDTO} into a {@link Profile} entity and vice versa. It also maps a {@link Profile} entity to a {@link ProfileResponseDTO} for returning responses.
 * </p>
 *
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.ProfileResponseDTO;
import br.com.gamehub.enums.Gender;
import br.com.gamehub.model.Profile;
import br.com.gamehub.util.Converter;

import java.time.LocalDateTime;


public class ProfileMapper {

    /**
     * Converts a {@link ProfileRequestDTO} to a {@link Profile} entity.
     *
     * @param dto The {@link ProfileRequestDTO} to be converted.
     * @return The corresponding {@link Profile} entity, or {@code null} if the input is {@code null}.
     */
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

    /**
     * Converts a {@link Profile} entity to a {@link ProfileResponseDTO}.
     *
     * @param profile The {@link Profile} entity to be converted.
     * @return The corresponding {@link ProfileResponseDTO}, or {@code null} if the input is {@code null}.
     */
    public static ProfileResponseDTO toResponse(Profile profile) {
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
