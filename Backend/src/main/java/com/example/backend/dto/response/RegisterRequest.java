package com.example.backend.dto.response;

public record RegisterRequest(
        String username,
        String password,
        String email,
        String fullName,
        Boolean gender,
        String phone,
        String address,
        int day,
        int month,
        int year
) {}
