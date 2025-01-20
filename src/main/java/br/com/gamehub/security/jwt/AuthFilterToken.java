package br.com.gamehub.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class is a filter for JWT token authentication. It intercepts incoming HTTP requests, extracts the JWT token from the
 * Authorization header, validates it, and sets the authentication in the Spring Security context.
 * <p>
 * The filter ensures that the user is authenticated before allowing access to secured endpoints.
 */
public class AuthFilterToken extends OncePerRequestFilter {

    // Logger for logging various events in the filter
    private static final Logger logger = LoggerFactory.getLogger(AuthFilterToken.class);

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor for injecting dependencies into the AuthFilterToken.
     *
     * @param jwtUtils Utility class for handling JWT token operations.
     * @param userDetailsService Service for loading user details based on the username.
     */
    @Autowired
    public AuthFilterToken(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is called for every incoming request. It checks if a valid JWT token is present in the Authorization header,
     * validates the token, and sets the authentication in the Spring Security context.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain that allows the request to continue.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = null;
        String email = null;

        try {
            // Obtain the JWT token from the request
            jwt = getToken(request);
            if (jwt != null) {
                logger.info("Token received: " + jwt);

                // Validate the JWT token
                if (jwtUtils.validateJwtToken(jwt)) {
                    email = jwtUtils.getEmailToken(jwt);
                    logger.info("Token is valid, user: " + email);

                    // Load the user details
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // Create authentication token
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Set the authentication details
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    logger.info("Authentication set for user: " + email);
                } else {
                    logger.warn("Invalid token received");
                }
            } else {
                logger.warn("No token found in request header");
            }

        } catch (Exception e) {
            logger.error("Error processing the token: " + e.getMessage(), e);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * This method extracts the JWT token from the Authorization header of the HTTP request.
     *
     * @param request The HTTP request containing the Authorization header.
     * @return The JWT token as a string, or null if no valid token is found.
     */
    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken)
                && (headerToken.startsWith("Bearer") || headerToken.startsWith("Bearer:"))) {
            String token = headerToken.replaceFirst("(?i)^Bearer[:\\s]*", "").trim();
            logger.info("Token found in header: " + token);
            return token;
        }
        logger.warn("No 'Bearer' token found in the Authorization header.");
        return null;
    }
}