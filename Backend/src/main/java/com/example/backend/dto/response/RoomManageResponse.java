package com.example.backend.dto.response;

import java.util.UUID;

public record RoomManageResponse(
        UUID id,
        String name,
        String status
) {
}
