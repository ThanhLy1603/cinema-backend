package com.example.backend.dto.request;

import java.util.UUID;

public record RoomMangeRequest(
        UUID id,
        String name,
        String status // "active", "closed", "maintenance"
) {}
