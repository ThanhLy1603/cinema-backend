package com.example.backend.dto.response;

import java.util.UUID;

public record SeatManageResponse(
        UUID id,
        String position,
        boolean active,
        RoomManageResponse room,
        SeatTypeManageResponse seatType
        ) {
}
