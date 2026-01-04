package com.example.backend.restapi;

import com.example.backend.controller.CustomerHistoryController;
import com.example.backend.dto.request.CustomerHistoryRequest;
import com.example.backend.dto.request.CustomerHistoryResponse;
import com.example.backend.dto.response.InvoiceDetailResponse;
import com.example.backend.dto.response.InvoiceSummaryResponse;
import com.example.backend.service.CustomerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/history")
@RequiredArgsConstructor
public class CustomerHistoryApi {
    private final CustomerHistoryService customerHistoryService;

//    @PostMapping("")
//    public ResponseEntity<List<CustomerHistoryResponse>> getCustomerHistory(CustomerHistoryRequest request) {
//        return ResponseEntity.ok(customerHistoryService.getCustomerHistory(request));
//    }

    @PostMapping("")
    public ResponseEntity<List<InvoiceSummaryResponse>> getInvoiceHistory(@RequestBody CustomerHistoryRequest request) {
        return ResponseEntity.ok(customerHistoryService.getAllInvoices(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailResponse> getInvoiceById(@PathVariable UUID id){
        return ResponseEntity.ok(customerHistoryService.getInvoiceDetail(id));
    }
}
