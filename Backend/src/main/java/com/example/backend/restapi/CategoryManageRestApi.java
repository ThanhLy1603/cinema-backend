package com.example.backend.restapi;

import com.example.backend.controller.CategoryController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CategoryManageResponse;
import com.example.backend.entity.Category;
import com.example.backend.service.CategoryManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryManageRestApi implements CategoryController {
    private final CategoryManageService categoryService;



    @Override
    @GetMapping("")
    public ResponseEntity<List<CategoryManageResponse>> getAllActive() {
        return ResponseEntity.ok(categoryService.getAllActive());
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
