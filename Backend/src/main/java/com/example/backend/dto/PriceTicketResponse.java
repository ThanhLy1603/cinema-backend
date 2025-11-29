package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PriceTicketResponse(
       UUID id,
       UUID filmId,
       String seatTypeName,
       BigDecimal price,
       LocalDate startDate,
       LocalDate endDate
) {
}
