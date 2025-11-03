package com.example.backend.restapi;

import com.example.backend.controller.ProductController;
import com.example.backend.dto.ProductResponse;
import com.example.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestApi implements ProductController {
    private final ProductService productService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
