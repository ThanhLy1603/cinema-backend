package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoice_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceTicket {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "ticket_price_id")
    private PriceTicket ticketPrice;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Builder.Default
    private Boolean isUsed = false;
    private LocalDateTime usedAt;
}
