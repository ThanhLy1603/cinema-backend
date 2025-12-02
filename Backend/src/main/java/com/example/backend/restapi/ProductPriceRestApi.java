package com.example.backend.restapi;

import com.example.backend.controller.ProductPriceController;
import com.example.backend.dto.ProductPriceResponse;
import com.example.backend.service.ProductPriceManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings/productprices")
public class ProductPriceRestApi implements ProductPriceController {
    private final ProductPriceManageService productPriceService;

    @GetMapping("")
    public ResponseEntity<List<ProductPriceResponse>> getAlls() {
        return ResponseEntity.ok(productPriceService.getAlls());
    }
}
