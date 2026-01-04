package com.example.backend.dto.response;

import java.util.UUID;

public record ProductResponse(UUID id, String name, String description, String poster) {
}
