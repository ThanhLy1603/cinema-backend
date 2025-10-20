package com.example.backend.dto;

public record ResetPasswordRequest(String email, String newPassword) {
}
