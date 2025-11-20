package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ProductPriceResponse;
import com.example.backend.entity.Product;
import com.example.backend.entity.ProductPrice;
import com.example.backend.repository.ProductPriceRepository;
import com.example.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPriceManageService {

    private final ProductPriceRepository productPriceRepo;
    private final ProductRepository productRepo;

    // ✅ Lấy tất cả giá chưa bị xóa
    @Transactional
    public List<ProductPriceResponse> getAlls() {
        return productPriceRepo.findByIsDeletedFalse()
                .stream()
                .map(this::toProductPriceResponse)
                .collect(Collectors.toList());
    }

    // ✅ Tạo mới giá cho sản phẩm
    @Transactional
    public ApiResponse create(ProductPrice priceRequest) {

        if (priceRequest.getProduct() == null || priceRequest.getProduct().getId() == null) {
            throw new RuntimeException("Thiếu productId!");
        }
        if (priceRequest.getPrice() == null) {
            throw new RuntimeException("Giá sản phẩm không được để trống!");
        }
        if (priceRequest.getStartDate() == null) {
            throw new RuntimeException("Ngày bắt đầu không được để trống!");
        }

        // Lấy product thật từ DB
        Product product = productRepo.findById(priceRequest.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        // Nếu đang có giá hiện tại (endDate = null) thì tự động đóng
        ProductPrice current = productPriceRepo.findByProductIdAndEndDateIsNullAndIsDeletedFalse(product.getId());
        if (current != null) {
            current.setEndDate(priceRequest.getStartDate().minusDays(1));
            productPriceRepo.save(current);
        }

        // Tạo giá mới
        ProductPrice newPrice = ProductPrice.builder()
                .product(product)
                .price(priceRequest.getPrice())
                .startDate(priceRequest.getStartDate())
                .endDate(priceRequest.getEndDate())
                .isDeleted(false)
                .build();

        productPriceRepo.save(newPrice);

        return new ApiResponse("success", "Thêm giá sản phẩm thành công!");
    }

    // ✅ Soft delete
    @Transactional
    public ApiResponse delete(UUID id) {
        ProductPrice price = productPriceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giá sản phẩm!"));

        price.setDeleted(true);
        productPriceRepo.save(price);

        return new ApiResponse("success", "Xóa giá sản phẩm thành công!");
    }

    // ✅ Mapper Entity → DTO
    private ProductPriceResponse toProductPriceResponse(ProductPrice pp) {
        return new ProductPriceResponse(
                pp.getId(),
                pp.getPrice(),
                pp.getStartDate(),
                pp.getEndDate(),
                pp.getProduct().getId()
        );
    }
}
