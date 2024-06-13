package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.commonTypes.UserRole;
import edu.ib.networktechnologies.exceptions.UserAlreadyExistsException;
import edu.ib.networktechnologies.controllers.dto.login.LoginDto;
import edu.ib.networktechnologies.controllers.dto.login.LoginResponseDto;
import edu.ib.networktechnologies.controllers.dto.register.RegisterDto;
import edu.ib.networktechnologies.controllers.dto.register.RegisterResponseDto;
import edu.ib.networktechnologies.entities.Auth;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.repositories.AuthRepository;
import edu.ib.networktechnologies.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    public final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthRepository authRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto) {
        Optional<Auth> existingAuth = authRepository.findByUsername(registerDto.getUsername());

        if(existingAuth.isPresent()) {
            throw UserAlreadyExistsException.create(registerDto.getUsername());
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setLastName(registerDto.getLastName());
       userRepository.save(user);

        Auth auth = new Auth();
        auth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        auth.setUsername(registerDto.getUsername());
        auth.setRole(UserRole.ROLE_READER);
        auth.setUser(user);

        String token = jwtService.generateToken(auth);
        authRepository.save(auth);

        return new RegisterResponseDto(auth.getId(), auth.getUsername(), token, auth.getRole().toString());
    }

    public LoginResponseDto login(LoginDto login) {
        Auth auth = authRepository.findByUsername(login.getUsername()).orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(login.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(auth);
        return new LoginResponseDto(token, auth.getUser().getUserId());
    }

    public void delete(long id) {
        authRepository.deleteByUserUserId(id);
    }
}