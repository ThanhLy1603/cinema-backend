package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record ShowTimeManageRequest(
        @JsonProperty("startTime") LocalTime startTime
) {}
