package com.example.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ShowTimeManageRequest(
        @JsonFormat(pattern = "HH:mm") LocalTime startTime
) {}
