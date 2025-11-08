package com.example.backend.dto;

import java.util.UUID;

public record FoodResponse(UUID id, String name, String description, String poster) {
}
