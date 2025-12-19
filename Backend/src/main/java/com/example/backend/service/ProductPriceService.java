package com.example.backend.service;

import com.example.backend.dto.response.ProductPriceResponse;
import com.example.backend.entity.ProductPrice;
import com.example.backend.repository.ProductPriceRepository;
import com.example.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductPriceResponse> getProductPrices(){
        return productPriceRepository.findByIsDeletedFalse()
                .stream()
                .map(this::toProductPriceResponse)
                .collect(Collectors.toList());
    }

    private ProductPriceResponse toProductPriceResponse(ProductPrice productPrice) {
        return new ProductPriceResponse(
                productPrice.getId(),
                productPrice.getPrice(),
                productPrice.getStartDate(),
                productPrice.getEndDate(),
                productPrice.getProduct().getId()
        );
    }
}
