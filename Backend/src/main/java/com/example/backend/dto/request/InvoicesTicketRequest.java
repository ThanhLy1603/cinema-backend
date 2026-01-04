package com.example.backend.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record InvoicesTicketRequest(UUID invoiceId,
                                    UUID scheduleId,
                                    UUID seatId,
                                    UUID ticketPriceId,
                                    BigDecimal price,
                                    UUID promotionId) {
}
