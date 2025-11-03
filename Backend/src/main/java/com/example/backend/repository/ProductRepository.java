package com.example.backend.repository;

import com.example.backend.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Food, UUID> {
    List<Food> findByIsDeletedFalse();
}
