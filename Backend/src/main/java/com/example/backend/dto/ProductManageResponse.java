package com.example.backend.dto;

import java.util.UUID;

public record ProductManageResponse(
        UUID id,
        String name,
        String description,
        String poster,
        Boolean isDeleted
) {}
