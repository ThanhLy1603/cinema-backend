package com.example.backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InvoiceProductRequest(UUID productId,
                                    Integer quantity,
                                    BigDecimal price,
                                    UUID promotionId) {
}
