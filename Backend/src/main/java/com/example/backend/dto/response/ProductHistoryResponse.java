package com.example.backend.dto.response;

import java.math.BigDecimal;

public record ProductHistoryResponse(
        String productName,
        Integer quantity,
        BigDecimal price,
        String promotionName
) {
}
