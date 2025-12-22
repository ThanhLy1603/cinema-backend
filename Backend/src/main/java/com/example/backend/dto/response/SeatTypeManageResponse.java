package com.example.backend.dto.response;

import java.util.UUID;

public record SeatTypeManageResponse(
        UUID id,
        String name
) {
}
