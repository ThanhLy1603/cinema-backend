package com.example.backend.restapi;

import com.example.backend.controller.InvoiceController;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.CreateInvoicesRequest;
import com.example.backend.service.InvoicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoicesRestApi implements InvoiceController {
    private final InvoicesService invoicesService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createInvoice(@RequestBody CreateInvoicesRequest request) {
        return ResponseEntity.ok(invoicesService.createInvoice(request));
    }
}
