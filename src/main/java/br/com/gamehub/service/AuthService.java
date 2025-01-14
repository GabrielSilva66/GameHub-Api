package br.com.gamehub.service;

import br.com.gamehub.dto.request.AuthenticationRequestDTO;
import br.com.gamehub.dto.response.AccessResponseDTO;
import br.com.gamehub.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Handles the login process by authenticating the user and generating a JWT token.
     *
     * This method attempts to authenticate the user based on the provided username and password.
     * If the authentication is successful, a JWT token is generated and returned in the response.
     * If the authentication fails (e.g., invalid username or password), an Unauthorized response
     * is returned with a relevant error message.
     *
     * @param authDTO The authentication request containing the username and password.
     * @return ResponseEntity containing the generated token or an error message.
     */
    public ResponseEntity<AccessResponseDTO> login(AuthenticationRequestDTO authDTO) {
        try {
            UsernamePasswordAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

            Authentication authentication = authenticationManager.authenticate(userAuth);
            UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new AccessResponseDTO(token, authentication.getName(), authorities ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AccessResponseDTO("Invalid email or password", null, null));
        }
    }
}
