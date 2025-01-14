package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.UserRequestDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.enums.UserType;
import br.com.gamehub.model.Profile;
import br.com.gamehub.model.User;
import br.com.gamehub.util.Converter;


public class UserMapper {

    // Converte UserRequestDTO para User (entidade)
    public static User toEntity(UserRequestDTO dto, Profile profile) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setEmail(dto.email());
        user.setPasswordHash(dto.passwordHash());

        if(dto.userType() != null){
            user.setUserType(Converter.stringToEnum(UserType.class, dto.userType()));
        }
        user.setUserType(Converter.stringToEnum(UserType.class, UserType.USER.toString()));

        user.setProfile(profile);

        return user;
    }

    // Converte User (entidade) para UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
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
