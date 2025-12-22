package com.example.backend.dto.request;

public record ResetPasswordRequest(String email, String newPassword) {
}
