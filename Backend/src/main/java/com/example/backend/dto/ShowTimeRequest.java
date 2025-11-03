package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
public record ShowTimeRequest(
        @JsonProperty("startTime") LocalTime startTime,
        @JsonProperty("isDeleted") Boolean isDeleted
) {}
