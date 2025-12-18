package com.example.backend.restapi;

import com.example.backend.controller.ProductPriceManageController;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.ProductPriceResponse;
import com.example.backend.entity.ProductPrice;
import com.example.backend.service.ProductPriceManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product-prices")
@RequiredArgsConstructor
public class ProductPriceManageRestApi implements ProductPriceManageController {

    private final ProductPriceManageService productPriceService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<ProductPriceResponse>> getAlls() {
        return ResponseEntity.ok(productPriceService.getAlls());
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody ProductPrice productPrice) {
        return ResponseEntity.ok(productPriceService.create(productPrice));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productPriceService.delete(id));
    }
}
