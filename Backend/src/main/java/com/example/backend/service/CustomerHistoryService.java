package com.example.backend.service;

import com.example.backend.dto.CustomerHistoryRequest;
import com.example.backend.dto.CustomerHistoryResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
}