package br.com.gamehub.security.jwt;


import br.com.gamehub.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtils {

    private static final Logger logger = LogManager.getLogger(JwtUtils.class);

    // Secret key used for signing the JWT token (loaded from application properties)
    @Value("${gamehub.jwtSecret}")
    private String jwstSecret;


    // Expiration time for the JWT token (in milliseconds, loaded from application properties)
    @Value("${gamehub.jwtExpirationMS}")
    private Long jwtExpirationMS;

    /**
     * Retrieves the signing key for JWT token generation and validation.
     * The signing key is decoded from the Base64 encoded secret.
     *
     * @return The signing key as a {@link Key}.
     */
    public Key getSignedKey() {
        logger.debug("Retrieving signing key...");
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwstSecret)); // Decoding and returning the signing key
    }


    /**
     * Generates a JWT token for the provided user details.
     * The token includes the email as the subject, the current time as the issue date, and the expiration time
     * based on the configured expiration period.
     *
     * @param userDetail The user details object used to generate the token.
     * @return The generated JWT token as a string.
     */
    public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
        logger.info("Generating token for user: {}", userDetail.getEmail());
        String token = Jwts.builder()
                .setSubject(userDetail.getEmail()) // Setting the email as the subject
                .setIssuedAt(new Date()) // Current date/time as the issued date
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMS)) // Expiration time based on configured value
                .signWith(getSignedKey(), SignatureAlgorithm.HS512) // Signing the token with HMAC SHA-512
                .compact(); // Compacting the JWT token into a string
        logger.debug("Token generated successfully: {}", token);
        return token;
    }

    /**
     * Extracts the email (subject) from the JWT token.
     *
     * @param authToken The JWT token.
     * @return The email extracted from the token.
     */
    public String getEmailToken(String authToken){
        return Jwts.parser()
                .setSigningKey(getSignedKey()) // Using the signing key to parse the token
                .build()
                .parseClaimsJws(authToken) // Parsing the JWT token
                .getBody() // Extracting the body of the token
                .getSubject(); // Getting the subject (email) from the body
    }


    /**
     * Validates the provided JWT token by checking its signature, expiration, and other claims.
     * If the token is valid, it returns true; otherwise, it logs the error and returns false.
     *
     * @param token The JWT token to be validated.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String token) {
        try {
            logger.info("Validating JWT token...");
            Jwts.parser()
                    .setSigningKey(getSignedKey()) // Using the signing key for validation
                    .build()
                    .parseClaimsJws(token); // Parsing the JWT token to validate it
            logger.info("Token is valid.");
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
