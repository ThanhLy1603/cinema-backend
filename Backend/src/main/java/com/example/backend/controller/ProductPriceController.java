package com.example.backend.controller;

import com.example.backend.dto.ProductPriceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProductPriceController {
    public ResponseEntity<List<ProductPriceResponse>> getAlls();
}
