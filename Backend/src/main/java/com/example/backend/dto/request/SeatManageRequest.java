package com.example.backend.dto.request;

import java.util.UUID;

public record SeatManageRequest(boolean active, UUID seatTypeId) {
}
