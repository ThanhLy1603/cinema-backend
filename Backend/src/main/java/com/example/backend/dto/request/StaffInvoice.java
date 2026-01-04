package com.example.backend.dto.request;

import java.math.BigDecimal;

public record StaffInvoice(
        String customerName,
        String customerAddress,
        String customerPhone,
        String username,
        BigDecimal totalAmount,
        BigDecimal discount,
        BigDecimal finalAmount
) {
}
