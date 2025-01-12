package br.com.gamehub.dto.response;

import br.com.gamehub.enums.UserType;


public record UserWishlistGameResponseDTO(
        Long id,
        String email,
        UserType userType
) {}
