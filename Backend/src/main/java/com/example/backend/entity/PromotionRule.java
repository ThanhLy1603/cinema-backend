package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "promotion_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRule {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType; // 'PERCENT','AMOUNT','BUY_X_GET_Y','FIXED_COMBO', 'TOTAL_PERCENT', 'TOTAL_AMOUNT'

    @Column(name = "rule_value", columnDefinition = "NVARCHAR(MAX)")
    private String ruleValue; // JSON: {"buy":2,"get":1} hoặc {"items":["popcorn","soda","nuggets"],"price":79000}
}
