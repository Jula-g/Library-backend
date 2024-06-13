package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.login.LoginDto;
import edu.ib.networktechnologies.controllers.dto.login.LoginResponseDto;
import edu.ib.networktechnologies.controllers.dto.register.RegisterDto;
import edu.ib.networktechnologies.controllers.dto.register.RegisterResponseDto;
import edu.ib.networktechnologies.services.AuthService;
import edu.ib.networktechnologies.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterDto register) {
      RegisterResponseDto response = authService.register(register);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto login) {
    LoginResponseDto dto = authService.login(login);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
