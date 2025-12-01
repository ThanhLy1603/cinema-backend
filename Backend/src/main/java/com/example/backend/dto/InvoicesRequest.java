package com.example.backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InvoicesRequest(String userName,
                              BigDecimal totalAmount,
                              BigDecimal discountAmount,
                              BigDecimal finalAmount) {
}
