package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 public class PromotionRuleRequest {
    private String ruleType;      // PERCENT, AMOUNT, BUY_X_GET_Y,...
    private Map<String,Object> ruleValue; // sẽ serialize JSON
}
