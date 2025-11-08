package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.FoodManageRequest;
import com.example.backend.dto.FoodManageResponse;
import com.example.backend.entity.Food;
import com.example.backend.repository.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodManageService {

    private final FoodRepository foodRepo;
    private final FileStorageService fileStorageService;

    // ✅ Lấy danh sách món ăn chưa bị xóa (isDeleted = false)
    @Transactional
    public List<FoodManageResponse> getAllActive() {
        return foodRepo.findByIsDeletedFalse().stream()
                .map(this::toFoodManageResponse)
                .collect(Collectors.toList());
    }

    // ✅ Thêm mới món ăn
    @Transactional
    public ApiResponse create(FoodManageRequest request) throws IOException {
        if (request.name() == null || request.name().trim().isEmpty()) {
            throw new RuntimeException("Tên sản phẩm không được để trống!");
        }

        boolean exists = foodRepo.findByIsDeletedFalse()
                .stream()
                .anyMatch(f -> f.getName().equalsIgnoreCase(request.name().trim()));

        if (exists) {
            throw new RuntimeException("Sản phẩm '" + request.name() + "' đã tồn tại!");
        }

        Food food = new Food();
        food.setName(request.name());
        food.setDescription(request.description());
        food.setIsDeleted(false);

        if (request.poster() != null && !request.poster().isEmpty()) {
            String poster = fileStorageService.saveFile(request.poster());
            food.setPoster(poster);
        }

        System.out.println("request: " + request);
        foodRepo.save(food);

        return new ApiResponse("success", "Thêm sản phẩm thành công!");
    }

    // ✅ Cập nhật món ăn
    @Transactional
    public ApiResponse update(UUID id, Food updatedFood) {
        Food existing = foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        if (updatedFood.getName() != null && !updatedFood.getName().trim().isEmpty()) {
            existing.setName(updatedFood.getName());
        }

        if (updatedFood.getDescription() != null) {
            existing.setDescription(updatedFood.getDescription());
        }

        if (updatedFood.getPoster() != null) {
            existing.setPoster(updatedFood.getPoster());
        }

        foodRepo.saveAndFlush(existing);
        return new ApiResponse("success", "Cập nhật sản phẩm thành công!");
    }

    // ✅ Xóa mềm (soft delete)
    @Transactional
    public ApiResponse delete(UUID id) {
        Food food = foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        food.setIsDeleted(true);
        foodRepo.saveAndFlush(food);
        return new ApiResponse("success", "Xóa sản phẩm thành công!");
    }

    // ✅ Mapper từ Entity → DTO
    private FoodManageResponse toFoodManageResponse(Food food) {
        return new FoodManageResponse(
                food.getId(),
                food.getName(),
                food.getDescription(),
                food.getPoster(),
                food.getIsDeleted()
        );
    }
}
