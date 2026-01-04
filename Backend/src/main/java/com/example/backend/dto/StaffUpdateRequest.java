package com.example.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record StaffUpdateRequest(
        String fullName,
        Boolean gender,
        String phone,
        String address,
        String birthday,
        MultipartFile avatar
) {
}
