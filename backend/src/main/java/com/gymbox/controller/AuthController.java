package com.gymbox.controller;

import com.gymbox.dto.AuthResponse;
import com.gymbox.dto.LoginRequest;
import com.gymbox.dto.RegisterRequest;
import com.gymbox.entity.User;
import com.gymbox.repository.UserRepository;
import com.gymbox.security.UserPrincipal;
import com.gymbox.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal UserPrincipal principal) {
        User user = userRepository.findById(principal.getId()).orElseThrow();
        return ResponseEntity.ok(user);
    }
}