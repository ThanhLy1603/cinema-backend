package com.example.backend.restapi;

import com.example.backend.controller.ProductPriceManageController;
import com.example.backend.dto.request.ProductPriceRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.ProductPriceResponse;
import com.example.backend.entity.ProductPrice;
import com.example.backend.service.ProductPriceManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product-prices")
@RequiredArgsConstructor
public class ProductPriceManageRestApi {

    private final ProductPriceManageService productPriceService;

    @GetMapping("")
    public ResponseEntity<List<ProductPriceResponse>> getAlls() {
        return ResponseEntity.ok(productPriceService.getAlls());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody ProductPriceRequest productPriceRequest) {
        return ResponseEntity.ok(productPriceService.create(productPriceRequest));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse> update(@RequestBody ProductPriceRequest productPriceRequest) {
        return ResponseEntity.ok(productPriceService.update(productPriceRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productPriceService.delete(id));
    }
}
