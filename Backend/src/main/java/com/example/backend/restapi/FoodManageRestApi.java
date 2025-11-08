package com.example.backend.restapi;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.FoodManageResponse;
import com.example.backend.entity.Food;
import com.example.backend.service.FoodManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/foods")
@RequiredArgsConstructor
public class FoodManageRestApi {

    private final FoodManageService foodService;

    @GetMapping("")
    public ResponseEntity<List<FoodManageResponse>> getAllActive() {
        return ResponseEntity.ok(foodService.getAllActive());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody Food food) {
        return ResponseEntity.ok(foodService.create(food));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody Food food) {
        return ResponseEntity.ok(foodService.update(id, food));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(foodService.delete(id));
    }
}
