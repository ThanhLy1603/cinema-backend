package com.example.backend.dto.request;

import java.util.UUID;

public record StaffTicketItem(
        UUID scheduleId,
        UUID seatId,
        UUID ticketPriceId,
        UUID promotionId
) {
}
