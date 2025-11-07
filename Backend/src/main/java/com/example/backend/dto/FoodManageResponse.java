package com.example.backend.dto;

import java.util.UUID;

public record FoodManageResponse(
        UUID id,
        String name,
        String description,
        String poster,
        Boolean isDeleted
) {}
