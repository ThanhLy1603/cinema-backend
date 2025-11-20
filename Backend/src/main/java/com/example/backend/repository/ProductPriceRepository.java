package com.example.backend.repository;

import com.example.backend.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, UUID> {
    // Lấy toàn bộ bản ghi chưa xóa

    List<ProductPrice> findByIsDeletedFalse();

    // Lấy lịch sử giá của 1 product
    List<ProductPrice> findByProductIdAndIsDeletedFalse(UUID productId);

    // Lấy giá hiện tại (end_date NULL)
    ProductPrice findByProductIdAndEndDateIsNullAndIsDeletedFalse(UUID productId);

}
