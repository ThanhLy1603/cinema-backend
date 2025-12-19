package com.example.backend.service;

<<<<<<< HEAD
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.CreateInvoicesRequest;
import com.example.backend.entity.Invoice;
import com.example.backend.entity.InvoiceProduct;
import com.example.backend.entity.InvoiceTicket;
import com.example.backend.entity.UserProfile;
=======
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CreateInvoicesRequest;
import com.example.backend.entity.*;
>>>>>>> origin/Quan
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.desktop.UserSessionEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoicesService {
    private final InvoiceRepository invoiceRepository;
    private final UserProfileRepository userProfileRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceTicketRepository invoiceTicketRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;
    private final PriceTicketRepository priceTicketRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ApiResponse createInvoice(CreateInvoicesRequest request) {
        UserProfile userProfile = userProfileRepository.findById(request.invoice().userName())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        Optional<Users> user = userRepository.findByUsername(request.invoice().userName());
        Users users = user.orElseThrow(() -> new RuntimeException("Users not found"));
        Invoice invoice = Invoice.builder()
                .customerName(userProfile.getUsername())
                .customerAddress(userProfile.getAddress())
                .customerPhone(userProfile.getPhone())
                .totalAmount(request.invoice().totalAmount())
                .discountAmount(request.invoice().discountAmount())
                .finalAmount(request.invoice().finalAmount())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .username(users)
                .build();
        invoiceRepository.save(invoice);

        List<InvoiceTicket> invoiceTickets = request.tickets().stream().map(ticketRequest -> {
            InvoiceTicket invoiceTicket = InvoiceTicket.builder()
                    .invoice(invoice)
                    .schedule(scheduleRepository.findById(ticketRequest.scheduleId())
                            .orElseThrow(() -> new RuntimeException("Schedule not found")))
                    .seat(seatRepository.findById(ticketRequest.seatId()).orElseThrow(() -> new RuntimeException("Seat not found")))
                    .ticketPrice(priceTicketRepository.findById(ticketRequest.ticketPriceId())
                            .orElseThrow(() -> new RuntimeException("PriceTicket not found")))
                    .price(ticketRequest.price())
                    .promotion(ticketRequest.promotionId() != null
                            ? promotionRepository.findById(ticketRequest.promotionId()).orElse(null)
                            : null)
                    .build();
            return invoiceTicket;
        }).toList();
        invoiceTicketRepository.saveAll(invoiceTickets);
        invoice.getTickets().addAll(invoiceTickets);

            List<InvoiceProduct> invoiceProducts = request.products().stream().map(productRequest -> {
                InvoiceProduct invoiceProduct = InvoiceProduct.builder()
                        .invoice(invoice)
                        .product(productRepository.findById(productRequest.productId()).orElseThrow(() -> new RuntimeException("Product not found")))
                        .quantity(productRequest.quantity())
                        .price(productRequest.price())
                        .promotion(productRequest.promotionId() != null
                                ? promotionRepository.findById(productRequest.productId()).orElse(null)
                                : null)
                        .build();
                return invoiceProduct;
            }).toList();
            invoiceProductRepository.saveAll(invoiceProducts);
        invoice.getProducts().addAll(invoiceProducts);

        // Lưu lại lần cuối để cascade quan hệ
        invoiceRepository.save(invoice);

        return new ApiResponse("success", "Invoice created successfully");
    }
}

