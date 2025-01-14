package br.com.gamehub.security.jwt;


import br.com.gamehub.service.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for accessing information about the current authenticated user.
 * It provides methods for retrieving the user details from the SecurityContext.
 * In case the user is not authenticated or unauthorized, an exception is thrown.
 */
public class SecurityUtils {

    // Logger instance for logging events in the SecurityUtils class
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    /**
     * Retrieves the currently authenticated user from the SecurityContext.
     * If the user is not authenticated or if the authentication principal is not an instance
     * of {@link UserDetailsImpl}, an exception is thrown.
     *
     * @return The authenticated user details.
     * @throws UnauthorizedUserException if no authenticated user is found or if the principal is not of type {@link UserDetailsImpl}.
     */
    public static UserDetailsImpl getCurrentUser() {
        logger.info("Attempting to retrieve the current authenticated user.");

        // Retrieve the current authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            logger.debug("Authentication object found: {}", authentication);

            // Check if the principal is of type UserDetailsImpl
            if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                logger.info("Authenticated user retrieved successfully: {}", userDetails.getUsername());
                return userDetails; // Return the authenticated user details
            } else {
                logger.warn("Authentication principal is not of type UserDetailsImpl. Principal type: {}",
                        authentication.getPrincipal().getClass().getSimpleName());
            }
        } else {
            logger.warn("No authentication object found in SecurityContext.");
        }

        // If authentication is not found or is not of the expected type, throw an exception
        logger.error("Unauthorized user attempt detected.");
        throw new UnauthorizedUserException("Unauthorized user");
    }

    /**
     * Custom exception class that is thrown when an unauthorized user is detected.
     * This exception is thrown when the current user cannot be found or if the user is not authenticated.
     */
    public static class UnauthorizedUserException extends RuntimeException {
        public UnauthorizedUserException(String message) {
            super(message);
            logger.error("UnauthorizedUserException thrown: {}", message);
        }
    }
}
