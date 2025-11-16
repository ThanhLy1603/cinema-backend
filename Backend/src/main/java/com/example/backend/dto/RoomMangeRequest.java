package com.example.backend.dto;

import java.util.UUID;

public record RoomMangeRequest(
        UUID id,
        String name,
        String status // "active", "closed", "maintenance"
) {}
