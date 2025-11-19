package com.example.backend.dto;

import com.example.backend.entity.SeatType;

import java.util.UUID;

public record SeatManageRequest(boolean active, UUID seatTypeId) {
}
