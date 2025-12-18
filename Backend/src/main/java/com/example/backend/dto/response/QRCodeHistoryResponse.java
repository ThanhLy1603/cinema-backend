package com.example.backend.dto.response;

import java.time.LocalDateTime;

public record QRCodeHistoryResponse(
        String qrCode,
        String qrType,
        Boolean isUsed,
        LocalDateTime usedAt
) {
}
