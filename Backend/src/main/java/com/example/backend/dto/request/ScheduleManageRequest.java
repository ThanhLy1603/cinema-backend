package com.example.backend.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record ScheduleManageRequest(
        UUID filmId,
        UUID roomId,
        UUID showTimeId,
        LocalDate scheduleDate
) {
}
