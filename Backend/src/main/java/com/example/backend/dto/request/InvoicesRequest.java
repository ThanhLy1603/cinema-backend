package com.example.backend.dto.request;

import java.math.BigDecimal;

public record InvoicesRequest(String userName,
                              BigDecimal totalAmount,
                              BigDecimal discountAmount,
                              BigDecimal finalAmount) {
}
