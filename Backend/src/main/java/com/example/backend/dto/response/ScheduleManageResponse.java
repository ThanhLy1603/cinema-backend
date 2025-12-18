package com.example.backend.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ScheduleManageResponse(
        UUID id,
        FilmResponse film,
        RoomManageResponse room,
        ShowTimeManageResponse showTime,
        LocalDate scheduleDate
) {
}
