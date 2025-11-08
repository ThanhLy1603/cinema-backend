package com.example.backend.service;

import com.example.backend.dto.FoodResponse;
import com.example.backend.entity.Food;
import com.example.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final ProductRepository productRepository;

    @Transactional
    public List<FoodResponse> getAllFoods() {
        List<FoodResponse> foods = productRepository.findByIsDeletedFalse().stream()
                .map(this::toFoodResponse)
                .collect(Collectors.toList());

        return foods;
    }

    private FoodResponse toFoodResponse(Food food) {
        return new FoodResponse(food.getId(),food.getName(), food.getDescription(), food.getPoster());
    }

    @Transactional
    public FoodResponse getFoodById(@PathVariable UUID id) {
        Food food = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return toFoodResponse(food);
    }
}
