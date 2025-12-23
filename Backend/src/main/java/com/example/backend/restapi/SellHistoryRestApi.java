package com.example.backend.restapi;

import com.example.backend.dto.response.InvoiceDetailResponse;
import com.example.backend.dto.response.InvoiceSummaryResponse;
import com.example.backend.service.SellHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/staff/sell-history")
public class SellHistoryRestApi {
    private final SellHistoryService sellHistoryService;

    @GetMapping("")
    public ResponseEntity<List<InvoiceSummaryResponse>> getAllInvoices(){
        return ResponseEntity.ok(sellHistoryService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailResponse> getDetailInvoice(@PathVariable UUID id){
        return ResponseEntity.ok(sellHistoryService.getInvoiceDetail(id));
    }
}
