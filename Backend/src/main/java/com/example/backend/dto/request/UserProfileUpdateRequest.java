package com.example.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserProfileUpdateRequest(String lastName,
                                       String firstName,
                                       Boolean gender,
                                       String phone,
                                       String address,
                                       int day,
                                       int month,
                                       int year,
                                       MultipartFile avatarUrl) {
}
