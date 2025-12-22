package com.example.backend.dto.response;

import java.util.UUID;

public record ProductManageResponse(
        UUID id,
        String name,
        String description,
        String poster,
        Boolean isDeleted
) {}
