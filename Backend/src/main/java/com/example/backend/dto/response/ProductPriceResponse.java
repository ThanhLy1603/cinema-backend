package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProductPriceResponse(
        UUID id,
        BigDecimal price,
        LocalDate startDate,
        LocalDate endDate,
        UUID productId
) {}
