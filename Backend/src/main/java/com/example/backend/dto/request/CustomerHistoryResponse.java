package com.example.backend.dto.request;

import com.example.backend.entity.Invoice;
import com.example.backend.entity.InvoiceProduct;
import com.example.backend.entity.InvoiceTicket;

import java.util.List;

public record CustomerHistoryResponse(Invoice invoice,
                                      List<InvoiceProduct> products,
                                      List<InvoiceTicket> tickets,
                                      String filmName) {
}
