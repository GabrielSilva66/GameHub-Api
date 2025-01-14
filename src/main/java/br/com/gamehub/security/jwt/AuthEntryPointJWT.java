package br.com.gamehub.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for intercepting unauthorized access attempts and
 * returning an error response with a 401 (Unauthorized) status.
 * It implements {@link AuthenticationEntryPoint} to customize the behavior
 * of the response when a user is not authenticated.
 */
@Component
public class AuthEntryPointJWT implements AuthenticationEntryPoint {

    // Logger to log unauthorized access attempts
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJWT.class);

    /**
     * This method is called when an unauthorized access attempt is made.
     * It generates a JSON response with the error details for the unauthorized access.
     *
     * @param request The HTTP request that was made.
     * @param response The HTTP response that will be sent.
     * @param authException The authentication exception that was thrown.
     * @throws IOException If an I/O error occurs while writing the response.
     * @throws ServletException If a servlet error occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // Log the unauthorized access attempt
        logger.error("Unauthorized access attempt: {}", request.getRequestURI(), authException);

        // Set the response content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Set the HTTP status code to 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a map with the error details
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getRequestURI());

        // Convert the map to JSON and write it to the response output stream
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
