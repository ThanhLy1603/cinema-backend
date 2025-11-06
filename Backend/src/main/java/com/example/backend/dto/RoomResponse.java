package com.example.backend.dto;

import java.util.UUID;

public record RoomResponse(
        UUID id,
        String name,
        String status,
        boolean isDeleted
) {
}
