package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CategoryManageResponse;
import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryManageService {

    private final CategoryRepository categoryRepo;

    // ✅ Lấy tất cả category chưa bị xóa (isDeleted = false)
    @Transactional
    public List<CategoryManageResponse> getAllActive() {
        return categoryRepo.findByIsDeletedFalse().stream()
                .map(this::toCategoryManageResponse)
                .collect(Collectors.toList());
    }

    // ✅ Tạo mới category
    @Transactional
    public ApiResponse create(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên danh mục không được để trống!");
        }

        boolean exists = categoryRepo.findByIsDeletedFalse()
                .stream()
                .anyMatch(c ->c.getName().equalsIgnoreCase(category.getName().trim()));

        if (exists) {
            throw new RuntimeException("Danh mục '" + category.getName() + "' đã tồn tại!");
        }

        category.setDeleted(false);
        categoryRepo.save(category);

        return new ApiResponse("success", "Thêm danh mục thành công!");
    }

    // ✅ Soft delete
    @Transactional
    public ApiResponse delete(UUID id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));
        category.setDeleted(true);
        categoryRepo.saveAndFlush(category);
        return new ApiResponse("success", "Xóa danh mục thành công!");
    }

    // ✅ Mapper chuyển entity → DTO
    private CategoryManageResponse toCategoryManageResponse(Category category) {
        return new CategoryManageResponse(
                category.getId(),
                category.getName());
    }
}
