package com.aman.expensemanager.controller;

import com.aman.expensemanager.dto.UserRequestDto;
import com.aman.expensemanager.dto.UserResponseDto;
import com.aman.expensemanager.entity.User;
import com.aman.expensemanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ REGISTER USER
    @PostMapping
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody UserRequestDto request
    ) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(userService.toDto(user));
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userService.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}