package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ProductPriceResponse;
import com.example.backend.entity.ProductPrice;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProductPriceManageController {

    // Lấy tất cả giá sản phẩm
    ResponseEntity<List<ProductPriceResponse>> getAlls();

    // Tạo giá sản phẩm mới
    ResponseEntity<ApiResponse> create(ProductPrice productPrice);

    // Xóa giá sản phẩm (soft delete)
    ResponseEntity<ApiResponse> delete(UUID id);
}
