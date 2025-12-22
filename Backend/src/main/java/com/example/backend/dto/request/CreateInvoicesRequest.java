package com.example.backend.dto.request;

import java.util.List;

public record CreateInvoicesRequest(InvoicesRequest invoice,
                                    List<InvoiceProductRequest> products,
                                    List<InvoicesTicketRequest> tickets) {
}
