package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponse {
        private UUID id;
        private String name;
        private String description;
        private String poster;
        private BigDecimal discountPercent;
        private BigDecimal discountAmount;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean active;
    private List<PromotionItemResponse> items;
    private List<PromotionRuleResponse> rules;
    private String errorMessage;
}
