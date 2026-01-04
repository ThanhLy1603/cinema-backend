package com.example.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record StaffCreateRequest(
        String username,
        String password,
        String email,
        String fullName,
        Boolean gender,
        String phone,
        String address,
        String birthday,
        MultipartFile avatar
) {
}
