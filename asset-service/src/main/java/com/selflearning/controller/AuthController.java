package com.selflearning.controller;
import com.selflearning.dto.RegisterRequestDto;
import com.selflearning.dto.UserResponseDto;
import com.selflearning.model.User;
import com.selflearning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Users", description = "Auth service APIs")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get All users", description = "Get All assets")
    public ResponseEntity<List<UserResponseDto>> getAllAssets() {
        return ResponseEntity.status(201).body(userService.getAllUsers()); // 201 Created
    }
}