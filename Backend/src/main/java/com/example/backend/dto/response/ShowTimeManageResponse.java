package com.example.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.UUID;

public record ShowTimeManageResponse(
        UUID id,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime
) {}
