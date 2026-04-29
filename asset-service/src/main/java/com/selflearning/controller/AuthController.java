package com.selflearning.controller;

import com.selflearning.config.JwtUtil;
import com.selflearning.dto.LoginRequestDto;
import com.selflearning.dto.TokenResponseDto;
import com.selflearning.dto.UserResponseDto;
import com.selflearning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Users", description = "Auth service APIs")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequestDto request) {
        userService.register(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) {
        log.info("Login attempt for: " + request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new TokenResponseDto(token));
    }

    @GetMapping
    @Operation(summary = "Get All users", description = "Get All assets")
    public ResponseEntity<List<UserResponseDto>> getAllAssets() {
        return ResponseEntity.status(201).body(userService.getAllUsers()); // 201 Created
    }
}