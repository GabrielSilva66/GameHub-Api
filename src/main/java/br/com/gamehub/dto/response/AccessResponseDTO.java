package br.com.gamehub.dto.response;

import java.util.List;

public record AccessResponseDTO(String token, String username, List<String> authorities) {

    public static AccessResponseDTO from(String token, String email, List<String> authorities) {
        return new AccessResponseDTO(token, email, authorities);
    }
}
