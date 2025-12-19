package com.example.backend.restapi;

import com.example.backend.dto.response.ProductPriceResponse;
import com.example.backend.service.ProductPriceManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-prices")
@RequiredArgsConstructor
public class ProductPriceRestApi {

    private final ProductPriceManageService productPriceService;

    @GetMapping("")
    public ResponseEntity<List<ProductPriceResponse>> getAlls() {
        return ResponseEntity.ok(productPriceService.getAlls());
    }
}
