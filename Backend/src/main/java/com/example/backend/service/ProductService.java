package com.example.backend.service;

import com.example.backend.dto.ProductResponse;
import com.example.backend.entity.Food;
import com.example.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> foods = productRepository.findByIsDeletedFalse().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());

        return foods;
    }

    private ProductResponse toProductResponse(Food food) {
        return new ProductResponse(food.getName(), food.getDescription(), food.getPoster());
    }
}
