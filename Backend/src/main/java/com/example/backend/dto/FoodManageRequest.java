package com.example.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record FoodManageRequest(
        String name,
        String description,
        MultipartFile poster
) {
}
