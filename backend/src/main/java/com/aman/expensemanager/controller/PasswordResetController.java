package com.aman.expensemanager.controller;

import com.aman.expensemanager.dto.ForgotPasswordRequest;
import com.aman.expensemanager.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class PasswordResetController {

    private final PasswordResetService resetService;

    public PasswordResetController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    // ✅ Forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        resetService.createResetToken(request.getUsername());
        return ResponseEntity.ok("Reset link sent email");
    }

    // ✅ Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody Map<String, String> req) {

        resetService.resetPassword(
                req.get("token"),
                req.get("password")
        );

        return ResponseEntity.ok("Password reset successful");
    }
}