package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users createdBy;

    @ManyToOne
    @JoinColumn(name = "username")
    private Users username;

    @Column(name = "customer_name", columnDefinition = "NVARCHAR(100)")
    private String customerName;

    @Column(name = "customer_phone", columnDefinition = "VARCHAR(10)")
    private String customerPhone;

    @Column(name = "customer_address", columnDefinition = "NVARCHAR(100)")
    private String customerAddress;

    private BigDecimal totalAmount;
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal finalAmount;

    @Builder.Default
    private String status = "PENDING";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<InvoiceTicket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<InvoiceProduct> products= new ArrayList<>();

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<InvoiceQRCode> qrcodes= new ArrayList<>();
}
