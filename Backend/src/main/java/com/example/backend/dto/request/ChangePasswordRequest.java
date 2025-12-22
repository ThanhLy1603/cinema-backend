package com.example.backend.dto.request;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword) {
}
