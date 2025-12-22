package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRequest {
    private String name;
    private String description;
    private MultipartFile posterFile;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PromotionItemRequest> items;
    private List<PromotionRuleRequest> rules;
}
