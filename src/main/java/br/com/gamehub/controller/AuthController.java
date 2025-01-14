package br.com.gamehub.controller;

import br.com.gamehub.dto.request.AuthenticationRequestDTO;
import br.com.gamehub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authRequestDTO){
        return ResponseEntity.ok(authService.login(authRequestDTO));
    }
}
