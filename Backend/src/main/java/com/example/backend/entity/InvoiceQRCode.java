package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoice_qrcodes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceQRCode {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(nullable = false)
    private String qrCode;

    @Builder.Default
    private String qrType = "COMBINED";

    @Builder.Default
    private Boolean isUsed = false;

    private LocalDateTime usedAt;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
