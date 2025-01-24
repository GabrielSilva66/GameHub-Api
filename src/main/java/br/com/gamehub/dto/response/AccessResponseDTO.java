package br.com.gamehub.dto.response;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the response for user authentication.
 * This DTO contains the authentication token, the username (or email), and a list of the user's authorities (roles or permissions).
 *
 * @param token the authentication token generated after successful login.
 * @param username the email of the authenticated user.
 * @param authorities the list of roles or authorities assigned to the authenticated user.
 */
public record AccessResponseDTO(String token, String username, List<String> authorities) {

    /**
     * Factory method to create an AccessResponseDTO from the given token, email, and authorities list.
     *
     * @param token the authentication token.
     * @param email the email of the authenticated user.
     * @param authorities the list of roles or authorities assigned to the user.
     * @return a new instance of AccessResponseDTO.
     */
    public static AccessResponseDTO from(String token, String email, List<String> authorities) {
        return new AccessResponseDTO(token, email, authorities);
    }
}
