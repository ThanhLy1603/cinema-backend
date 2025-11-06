package com.example.backend.controller;

import com.example.backend.dto.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductController {
    public ResponseEntity<List<ProductResponse>> getAllProducts();
}
