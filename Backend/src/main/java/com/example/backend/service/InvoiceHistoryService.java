package com.example.backend.service;

import com.example.backend.dto.response.*;
import com.example.backend.entity.Invoice;
import com.example.backend.entity.InvoiceProduct;
import com.example.backend.entity.InvoiceQRCode;
import com.example.backend.entity.InvoiceTicket;
import com.example.backend.repository.InvoiceProductRepository;
import com.example.backend.repository.InvoiceQRCodeRepository;
import com.example.backend.repository.InvoiceRepository;
import com.example.backend.repository.InvoiceTicketRepository;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceHistoryService {
    private final InvoiceRepository invoiceRepository;

    public List<InvoiceSummaryResponse> getAllInvoices() {

        return invoiceRepository.findByIsDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(invoice -> new InvoiceSummaryResponse(
                        invoice.getId(),
                        invoice.getCreatedAt(),
                        invoice.getStatus(),
                        invoice.getFinalAmount(),
                        invoice.getCreatedBy() != null ? invoice.getCreatedBy().getUsername() : invoice.getUsername().getUsername(),
                        invoice.getCustomerName(),
                        invoice.getCustomerPhone()
                ))
                .toList();
    }

    public InvoiceDetailResponse getInvoiceDetail(UUID id) {
        Invoice invoice = invoiceRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        return toInvoiceDetailResponse(invoice);
    }

    private InvoiceDetailResponse toInvoiceDetailResponse(Invoice invoice) {
        return new InvoiceDetailResponse(
                invoice.getId(),
                invoice.getStatus(),
                invoice.getCreatedAt(),
                invoice.getTotalAmount(),
                invoice.getDiscountAmount(),
                invoice.getFinalAmount(),
                invoice.getCreatedBy() != null ? invoice.getCreatedBy().getUsername() : invoice.getUsername().getUsername(),
                invoice.getCustomerName(),
                invoice.getCustomerPhone(),

                invoice.getTickets()
                        .stream()
                        .map(this::toTicketHistoryResponse)
                        .toList(),

                invoice.getProducts()
                        .stream()
                        .map(this::toProductHistoryResponse)
                        .toList(),

                invoice.getQrcodes()
                        .stream()
                        .map(this::toQRCodeHistoryResponse)
                        .toList()
        );
    }

    private QRCodeHistoryResponse toQRCodeHistoryResponse(InvoiceQRCode qrCode) {
        return new QRCodeHistoryResponse(
              qrCode.getQrCode(),
              qrCode.getQrType(),
              qrCode.getIsUsed(),
              qrCode.getUsedAt()
        );
    }

    private ProductHistoryResponse toProductHistoryResponse(InvoiceProduct invoiceProduct) {
        return new ProductHistoryResponse(
                invoiceProduct.getProduct().getName(),
                invoiceProduct.getQuantity(),
                invoiceProduct.getPrice(),
                invoiceProduct.getPromotion() != null ? invoiceProduct.getPromotion().getName() : ""
        );
    }

    private TicketHistoryResponse toTicketHistoryResponse(InvoiceTicket ticket) {
        return new TicketHistoryResponse(
                ticket.getSchedule().getFilm().getName(),
                ticket.getSchedule().getShowTime().getStartTime(),
                ticket.getSchedule().getRoom().getName(),
                ticket.getSeat().getPosition(),
                ticket.getPrice(),
                ticket.getIsUsed(),
                ticket.getUsedAt()
        );
    }
}
