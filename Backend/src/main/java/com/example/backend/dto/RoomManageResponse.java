package com.example.backend.dto;

import java.util.UUID;

public record RoomManageResponse(
        UUID id,
        String name,
        String status
) {
}
