package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.CreateInvoicesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface InvoiceController {
    public ResponseEntity<ApiResponse> createInvoice(@RequestBody CreateInvoicesRequest request);
}
