package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RevenueByDateResponse(
        LocalDate date,
        BigDecimal revenue
) {
}
