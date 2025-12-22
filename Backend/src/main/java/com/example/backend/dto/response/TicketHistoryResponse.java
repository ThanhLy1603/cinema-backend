package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TicketHistoryResponse(
        String movieName,
        LocalTime showTime,
        String roomName,
        String seatPosition,
        BigDecimal price,
        Boolean isUsed,
        LocalDateTime usedAt
) {
}
