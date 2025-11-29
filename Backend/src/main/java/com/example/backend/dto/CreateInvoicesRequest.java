package com.example.backend.dto;

import java.util.List;

public record CreateInvoicesRequest(InvoicesRequest invoice,
                                    List<InvoiceProductRequest> products,
                                    List<InvoicesTicketRequest> tickets) {
}
