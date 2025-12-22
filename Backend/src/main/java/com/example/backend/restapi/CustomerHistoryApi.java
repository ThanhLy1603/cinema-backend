package com.example.backend.restapi;

import com.example.backend.controller.CustomerHistoryController;
import com.example.backend.dto.CustomerHistoryRequest;
import com.example.backend.dto.CustomerHistoryResponse;
import com.example.backend.service.CustomerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/history")
@RequiredArgsConstructor
public class CustomerHistoryApi implements CustomerHistoryController {
    private final CustomerHistoryService customerHistoryService;
    @PostMapping("")
    public ResponseEntity<List<CustomerHistoryResponse>> getCustomerHistory(CustomerHistoryRequest request) {
        return ResponseEntity.ok(customerHistoryService.getCustomerHistory(request));
    }
}
