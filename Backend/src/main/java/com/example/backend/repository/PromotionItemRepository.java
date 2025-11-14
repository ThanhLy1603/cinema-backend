package com.example.backend.repository;

import com.example.backend.entity.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromotionItemRepository extends JpaRepository<PromotionItem, UUID> {
}
