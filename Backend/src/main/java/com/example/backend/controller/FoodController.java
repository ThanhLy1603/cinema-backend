package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.FoodManageResponse;
import com.example.backend.entity.Food;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface FoodController {

    ResponseEntity<List<FoodManageResponse>> getAllActive();

    ResponseEntity<ApiResponse> create(Food food);

    ResponseEntity<ApiResponse> update(UUID id, Food food);

    ResponseEntity<ApiResponse> delete(UUID id);
}
