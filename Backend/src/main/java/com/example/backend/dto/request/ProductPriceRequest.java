package com.example.backend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProductPriceRequest(
        UUID productId,
        BigDecimal price,
        LocalDate startDate,
        LocalDate endDate
) {
}
