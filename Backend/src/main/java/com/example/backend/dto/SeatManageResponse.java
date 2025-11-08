package com.example.backend.dto;

import com.example.backend.entity.SeatType;

import java.util.UUID;

public record SeatManageResponse(
        UUID id,
        String position,
        RoomManageResponse room,
        SeatTypeManageResponse seatType
        ) {
}
