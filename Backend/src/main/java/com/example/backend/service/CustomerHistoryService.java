package com.example.backend.service;

import com.example.backend.dto.request.CustomerHistoryRequest;
import com.example.backend.dto.request.CustomerHistoryResponse;
import com.example.backend.dto.response.*;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerHistoryService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceTicketRepository invoiceTicketRepository;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    public List<CustomerHistoryResponse> getCustomerHistory(CustomerHistoryRequest request) {
        // Validate request
        if (request == null || request.username() == null || request.username().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        String username = request.username().trim();
        System.out.println("Received username: " + username);

        // Tìm user
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng với username: " + username));

        // Lấy danh sách hóa đơn
        List<Invoice> invoices = invoiceRepository.findAllByUsernameUsername(user.getUsername());
        invoices.stream().map(in-> {
            System.out.println("Found invoice ID: " + in.getId());
            return in;
        }).toList();
        if (invoices.isEmpty()) {
            return List.of();
        }

        // Xử lý từng hóa đơn
        return invoices.stream().map(invoice -> {
            // Lấy products và tickets
            List<InvoiceProduct> products = invoiceProductRepository.findByInvoiceId(invoice.getId());
            List<InvoiceTicket> tickets = invoiceTicketRepository.findByInvoiceId(invoice.getId());

            // Lấy Film – an toàn với null
            Film film = null;
            if (!tickets.isEmpty()) {
                InvoiceTicket firstTicket = tickets.get(0); // lấy vé đầu tiên
                if (firstTicket.getTicketPrice() != null && firstTicket.getTicketPrice().getFilm() != null) {
                    UUID filmId = firstTicket.getTicketPrice().getFilm().getId();
                    film = filmRepository.findFilmByIdAndIsDeletedFalse(filmId);
                }
            }


            return new CustomerHistoryResponse(invoice, products, tickets, film.getName());

        }).toList();
    }

    public List<InvoiceSummaryResponse> getAllInvoices(CustomerHistoryRequest request) {

        return invoiceRepository.findAllByUsername_UsernameOrderByCreatedAtDesc(request.username())
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
                ticket.getSchedule().getScheduleDate(),
                ticket.getSchedule().getRoom().getName(),
                ticket.getSeat().getPosition(),
                ticket.getPrice(),
                ticket.getIsUsed(),
                ticket.getUsedAt()
        );
    }
}