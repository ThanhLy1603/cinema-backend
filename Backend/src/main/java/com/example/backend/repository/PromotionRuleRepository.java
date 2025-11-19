package com.example.backend.repository;

import com.example.backend.entity.PromotionRule;
import com.example.backend.enums.PromotionRuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PromotionRuleRepository extends JpaRepository<PromotionRule, UUID> {
    @Query("SELECT DISTINCT r.ruleType FROM PromotionRule r")
    List<PromotionRuleType> findDistinctRuleTypes();
}
