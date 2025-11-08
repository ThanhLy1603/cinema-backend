package com.example.backend.controller;

import com.example.backend.dto.FoodResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface FoodController {
    public ResponseEntity<List<FoodResponse>> getAllFoods();
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable UUID id);
}
