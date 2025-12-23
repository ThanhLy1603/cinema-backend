package com.example.backend.restapi;

import com.example.backend.controller.ProductManageController;
import com.example.backend.dto.request.ProductPriceRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ProductManageRequest;
import com.example.backend.dto.response.ProductManageResponse;
import com.example.backend.service.ProductManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductManageRestApi implements ProductManageController {

    private final ProductManageService productManageService;

    @GetMapping("")
    public ResponseEntity<List<ProductManageResponse>> getAllActive() {
        return ResponseEntity.ok(productManageService.getAllActive());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody ProductManageRequest request) throws IOException {
        return ResponseEntity.ok(productManageService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productManageService.delete(id));
    }
}
