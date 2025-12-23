package com.example.backend.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record StaffProductItem (
        UUID productId,
        Integer quantity,
        BigDecimal price,
        UUID promotionId
) {
}
