package com.example.backend.restapi;

import com.example.backend.dto.response.InvoiceDetailResponse;
import com.example.backend.dto.response.InvoiceSummaryResponse;
import com.example.backend.service.InvoiceHistoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/invoices")
@RequiredArgsConstructor
public class InvoiceHistoryRestApi {
    private final InvoiceHistoryService invoiceHistoryService;

    @GetMapping("")
    public ResponseEntity<List<InvoiceSummaryResponse>> getAllInvoices(){
        return ResponseEntity.ok(invoiceHistoryService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailResponse> getInvoiceById(@PathVariable UUID id){
        return ResponseEntity.ok(invoiceHistoryService.getInvoiceDetail(id));
    }
}
