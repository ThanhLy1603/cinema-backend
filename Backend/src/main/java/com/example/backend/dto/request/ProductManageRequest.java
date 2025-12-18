package com.example.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ProductManageRequest(
        String name,
        String description,
        MultipartFile poster
) {
}
