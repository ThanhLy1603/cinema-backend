package com.example.backend.service;

import com.example.backend.dto.request.ProductPriceRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.ProductPriceResponse;
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
    public ApiResponse create(ProductPriceRequest priceRequest) {

        // Lấy product thật từ DB
        Product product = productRepo.findById(priceRequest.productId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        if (product == null) {
            return new ApiResponse("fail", "Không tìm thấy sản phẩm");
        }

        // Nếu đang có giá hiện tại (endDate = null) thì tự động đóng
        ProductPrice current = productPriceRepo.findByProductIdAndEndDateIsNullAndIsDeletedFalse(product.getId());
        if (current != null) {
            current.setEndDate(priceRequest.startDate().minusDays(1));
            productPriceRepo.save(current);
        }

        // Tạo giá mới
        ProductPrice newPrice = ProductPrice.builder()
                .product(product)
                .price(priceRequest.price())
                .startDate(priceRequest.startDate())
                .endDate(priceRequest.endDate())
                .isDeleted(false)
                .build();

        productPriceRepo.save(newPrice);

        return new ApiResponse("success", "Thêm giá sản phẩm thành công!");
    }

    public ApiResponse update(ProductPriceRequest priceRequest) {
        Product product = productRepo.findById(priceRequest.productId()).orElse(null);

        ProductPrice productPrice = productPriceRepo.findByProductIdAndEndDateIsNullAndIsDeletedFalse(priceRequest.productId());

        if (product == null) return new ApiResponse("fail", "Không tìm thấy sản phẩm");
        if (productPrice == null) return new ApiResponse("fail", "Không tìm thấy giá sản phẩm");

        productPrice.setProduct(product);
        productPrice.setPrice(priceRequest.price());
        productPrice.setStartDate(priceRequest.startDate());
        productPrice.setEndDate(priceRequest.endDate());
        productPriceRepo.save(productPrice);

        return new ApiResponse("success", "Thêm sản phẩm thành công");
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
