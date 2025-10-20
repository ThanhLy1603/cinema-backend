package com.example.backend.dto;

public record RegisterRequest(
        String username,
        String password,
        String fullName,
        Boolean gender,
        String phone,
        String address,
        int ngay,
        int thang,
        int nam
) {}
