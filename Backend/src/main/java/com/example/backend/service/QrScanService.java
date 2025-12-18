package com.example.backend.service;

import com.beust.ah.A;
import com.example.backend.dto.request.ScanQrRequest;
import com.example.backend.dto.response.*;
import com.example.backend.entity.Invoice;
import com.example.backend.entity.InvoiceProduct;
import com.example.backend.entity.InvoiceQRCode;
import com.example.backend.entity.InvoiceTicket;
import com.example.backend.repository.InvoiceQRCodeRepository;
import com.example.backend.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QrScanService {
    private final InvoiceQRCodeRepository invoiceQRCodeRepository;
    private final InvoiceRepository invoiceRepository;

    public ScanQrResponse scanQr(ScanQrRequest request) {
        InvoiceQRCode invoiceQRCode = invoiceQRCodeRepository.findByQrCode(request.qrCode());

        if (invoiceQRCode == null) throw new RuntimeException("QR code không tồn tại");

        Invoice invoice = invoiceQRCode.getInvoice();

        return new ScanQrResponse(
              true,
              "Xác nhận hoá đơn thành công",
              toInvoiceDetailResponse(invoice)
        );
    }

    @Transactional
    public ApiResponse confirmQRCode(ScanQrRequest request) {
        InvoiceQRCode invoiceQRCode = invoiceQRCodeRepository.findByQrCode(request.qrCode());

        if (invoiceQRCode == null) return new ApiResponse("fail","QR không tồn tại");

        if (Boolean.TRUE.equals(invoiceQRCode.getIsUsed())) return new ApiResponse("fail", "QR đã được sử dụng");

        Invoice invoice = invoiceQRCode.getInvoice();

        if ("CHECKED_IN".equals(invoice.getStatus())) return new  ApiResponse("fail", "Hoá đơn đã được sử dụng");

        invoiceQRCode.setIsUsed(true);
        invoiceQRCode.setUsedAt(LocalDateTime.now());
        invoice.setStatus("CHECKED_IN");
        invoiceQRCodeRepository.save(invoiceQRCode);
        invoiceRepository.save(invoice);

        return new ApiResponse("success", "Soát vé thành công");
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

    private InvoiceSummaryResponse toInvoiceSummaryResponse(Invoice invoice) {
        return new InvoiceSummaryResponse(
                invoice.getId(),
                invoice.getCreatedAt(),
                invoice.getStatus(),
                invoice.getFinalAmount(),
                invoice.getCreatedBy() != null ? invoice.getCreatedBy().getUsername() : invoice.getUsername().getUsername(),
                invoice.getCustomerName(),
                invoice.getCustomerPhone()
        );
    }
}
