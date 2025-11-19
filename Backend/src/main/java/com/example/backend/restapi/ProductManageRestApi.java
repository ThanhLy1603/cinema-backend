package com.example.backend.restapi;

import com.example.backend.controller.ProductManageController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ProductManageRequest;
import com.example.backend.dto.ProductManageResponse;
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
    public ResponseEntity<ApiResponse> create(@ModelAttribute ProductManageRequest request) throws IOException {
        return ResponseEntity.ok(productManageService.create(request));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody Product food) {
//        return ResponseEntity.ok(productManageService.update(id, food));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productManageService.delete(id));
    }
}
