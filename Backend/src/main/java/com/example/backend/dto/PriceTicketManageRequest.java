package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PriceTicketManageRequest(
        UUID filmId,
        UUID seatTypeId,
        UUID showTimeId,
        String dayType,
        BigDecimal price,
        LocalDate startDate,
        LocalDate endDate
) {
}
