package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvoiceSummaryResponse(
        UUID id,
        LocalDateTime createAt,
        String status,
        BigDecimal finalAmount,
        String createdBy,
        String customerName,
        String customerPhone
) {
}
