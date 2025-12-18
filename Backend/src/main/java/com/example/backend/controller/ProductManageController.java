package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ProductManageRequest;
import com.example.backend.dto.response.ProductManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductManageController {
    ResponseEntity<List<ProductManageResponse>> getAllActive();
    ResponseEntity<ApiResponse> create(@ModelAttribute ProductManageRequest request) throws IOException;
//    ResponseEntity<ApiResponse> update(UUID id, Product product);
    ResponseEntity<ApiResponse> delete(UUID id);
}
