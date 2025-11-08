package com.example.backend.restapi;

import com.example.backend.controller.FoodController;
import com.example.backend.dto.FoodResponse;
import com.example.backend.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodRestApi implements FoodController {
    private final FoodService foodService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<FoodResponse>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable UUID id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }
}
