package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record InvoiceDetailResponse(
        UUID invoiceId,
        String status,
        LocalDateTime createdAt,
        BigDecimal totalAmount,
        BigDecimal discountAmount,
        BigDecimal finalAmount,
        String createdBy,
        String customerName,
        String customerPhone,
        List<TicketHistoryResponse> tickets,
        List<ProductHistoryResponse> products,
        List<QRCodeHistoryResponse> qrCodes
) {
}
