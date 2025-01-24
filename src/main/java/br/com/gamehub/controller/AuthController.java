package br.com.gamehub.controller;

import br.com.gamehub.dto.request.AuthenticationRequestDTO;
import br.com.gamehub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for user authentication operations.
 * Provides an endpoint for logging in users.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor for the AuthController.
     *
     * @param authService The authentication service to be injected by Spring.
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Logs in a user by validating the credentials and generating a token.
     *
     * This endpoint receives a request with the user's credentials (username and password),
     * and if the credentials are valid, it generates an authentication token and returns it in the response.
     *
     * @param authRequestDTO The authentication request data transfer object containing the user's credentials.
     * @return A response containing the authentication token if the login is successful, with status 200 (OK).
     * @throws InvalidRequestException If the credentials are invalid.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.login(authRequestDTO));
    }
}
