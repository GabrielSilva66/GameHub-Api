/**
 * Utility class for mapping between {@link UserRequestDTO}, {@link User} entity, and {@link UserResponseDTO}.
 *
 * <p>
 * This class provides methods to convert a {@link UserRequestDTO} into a {@link User} entity and vice versa. It also maps a {@link User} entity to a {@link UserResponseDTO} for returning responses.
 * </p>
 *
 * @since 2025-01-23
 */package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.UserRequestDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.enums.UserType;
import br.com.gamehub.model.Profile;
import br.com.gamehub.model.User;
import br.com.gamehub.util.Converter;


public class UserMapper {

    /**
     * Converts a {@link UserRequestDTO} to a {@link User} entity.
     *
     * @param dto The {@link UserRequestDTO} to be converted.
     * @param profile The {@link Profile} entity associated with the user.
     * @return The corresponding {@link User} entity, or {@code null} if the input is {@code null}.
     */
    public static User toEntity(UserRequestDTO dto, Profile profile) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setEmail(dto.email());
        user.setPasswordHash(dto.passwordHash());

        if (dto.userType() != null) {
            user.setUserType(Converter.stringToEnum(UserType.class, dto.userType()));
        } else {
            user.setUserType(Converter.stringToEnum(UserType.class, UserType.COMMON.toString()));
        }

        user.setProfile(profile);

        return user;
    }

    /**
     * Converts a {@link User} entity to a {@link UserResponseDTO}.
     *
     * @param user The {@link User} entity to be converted.
     * @return The corresponding {@link UserResponseDTO}, or {@code null} if the input is {@code null}.
     */
    public static UserResponseDTO toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUserType()
        );
    }
}
