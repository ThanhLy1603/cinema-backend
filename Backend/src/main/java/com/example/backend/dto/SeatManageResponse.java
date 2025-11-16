package com.example.backend.dto;

import com.example.backend.entity.SeatType;

import java.util.UUID;

public record SeatManageResponse(
        UUID id,
        String position,
        boolean active,
        RoomManageResponse room,
        SeatTypeManageResponse seatType
        ) {
}
