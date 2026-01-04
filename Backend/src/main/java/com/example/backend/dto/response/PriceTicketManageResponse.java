package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PriceTicketManageResponse(
        UUID id,
        FilmResponse film,
        SeatTypeManageResponse seatType,
        ShowTimeManageResponse showTime,
        String dayType,
        BigDecimal price,
        LocalDate startDate,
        LocalDate endDate
) {
}