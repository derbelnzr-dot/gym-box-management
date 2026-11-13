package com.gymbox.service;

import com.gymbox.dto.AuthResponse;
import com.gymbox.dto.LoginRequest;
import com.gymbox.dto.RegisterRequest;
import com.gymbox.entity.User;
import com.gymbox.enums.UserRole;
import com.gymbox.repository.UserRepository;
import com.gymbox.security.JwtService;
import com.gymbox.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Un compte existe déjà avec cet email.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris.");
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.MEMBER)
                .isActive(true)
                .plan("Standard")
                .points(0)
                .status("Active")
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(new UserPrincipal(user));
        return new AuthResponse(token, user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().trim().toLowerCase(),
                        request.getPassword())
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        String token = jwtService.generateToken(principal);

        return new AuthResponse(token, user);
    }
}