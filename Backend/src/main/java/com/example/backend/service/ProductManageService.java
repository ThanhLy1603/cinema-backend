package com.example.backend.service;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ProductManageRequest;
import com.example.backend.dto.response.ProductManageResponse;
import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductManageService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    // ✅ Lấy danh sách món ăn chưa bị xóa (isDeleted = false)
    @Transactional
    public List<ProductManageResponse> getAllActive() {
        return productRepository.findByIsDeletedFalse().stream()
                .map(this::toFoodManageResponse)
                .collect(Collectors.toList());
    }

    // ✅ Thêm mới món ăn
    @Transactional
    public ApiResponse create(ProductManageRequest request) throws IOException {
        if (request.name() == null || request.name().trim().isEmpty()) {
            throw new RuntimeException("Tên sản phẩm không được để trống!");
        }

        boolean exists = productRepository.findByIsDeletedFalse()
                .stream()
                .anyMatch(f -> f.getName().equalsIgnoreCase(request.name().trim()));

        if (exists) {
            throw new RuntimeException("Sản phẩm '" + request.name() + "' đã tồn tại!");
        }

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setIsDeleted(false);

        if (request.poster() != null && !request.poster().isEmpty()) {
            String poster = fileStorageService.saveFile(request.poster());
            product.setPoster(poster);
        }

        System.out.println("request: " + request);
        productRepository.save(product);

        return new ApiResponse("success", "Thêm sản phẩm thành công!");
    }

    // ✅ Cập nhật món ăn
    @Transactional
    public ApiResponse update(UUID id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        if (updatedProduct.getName() != null && !updatedProduct.getName().trim().isEmpty()) {
            existing.setName(updatedProduct.getName());
        }

        if (updatedProduct.getDescription() != null) {
            existing.setDescription(updatedProduct.getDescription());
        }

        if (updatedProduct.getPoster() != null) {
            existing.setPoster(updatedProduct.getPoster());
        }

        productRepository.saveAndFlush(existing);
        return new ApiResponse("success", "Cập nhật sản phẩm thành công!");
    }

    // ✅ Xóa mềm (soft delete)
    @Transactional
    public ApiResponse delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        product.setIsDeleted(true);
        productRepository.saveAndFlush(product);
        return new ApiResponse("success", "Xóa sản phẩm thành công!");
    }

    // ✅ Mapper từ Entity → DTO
    private ProductManageResponse toFoodManageResponse(Product product) {
        return new ProductManageResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPoster(),
                product.getIsDeleted()
        );
    }
}
