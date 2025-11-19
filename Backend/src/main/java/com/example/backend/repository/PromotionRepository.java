package com.example.backend.repository;

import com.example.backend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    Optional<Promotion> findTopByActiveTrueAndIsDeletedFalse();
    List<Promotion> findAllByIsDeletedFalse();
}
