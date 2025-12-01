package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CreateInvoicesRequest;
import com.example.backend.dto.InvoicesRequest;
import com.example.backend.entity.InvoiceProduct;
import com.example.backend.entity.InvoiceTicket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InvoiceController {
    public ResponseEntity<ApiResponse> createInvoice(@RequestBody CreateInvoicesRequest request);
}
