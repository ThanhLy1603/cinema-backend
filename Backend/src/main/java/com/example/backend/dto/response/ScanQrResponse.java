package com.example.backend.dto.response;

public record ScanQrResponse(
        boolean success,
        String message,
        InvoiceDetailResponse invoice
) {
}
