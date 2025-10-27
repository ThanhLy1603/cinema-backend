package com.example.backend.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword) {
}
