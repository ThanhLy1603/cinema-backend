package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.util.UUID;

public record ShowTimeManageResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("startTime") LocalTime startTime
) {}
