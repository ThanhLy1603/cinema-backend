package com.example.backend.controller;

import com.example.backend.dto.request.CustomerHistoryRequest;
import com.example.backend.dto.request.CustomerHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerHistoryController {
    public ResponseEntity<List<CustomerHistoryResponse>> getCustomerHistory(@RequestBody CustomerHistoryRequest username);
}
