package com.example.backend.controller;

import com.example.backend.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface ProductController {
    public ResponseEntity<List<ProductResponse>> getAllProducts();
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id);
}
