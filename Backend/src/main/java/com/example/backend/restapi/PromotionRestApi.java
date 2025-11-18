package com.example.backend.restapi;

import com.example.backend.controller.PromotionController;
import com.example.backend.dto.*;
import com.example.backend.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/promotions")
@RequiredArgsConstructor
public class PromotionRestApi implements PromotionController {

    private final PromotionService promotionService;

    @GetMapping("")
    @Override
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable UUID id) {
        return ResponseEntity.ok(promotionService.getPromotion(id));
    }



    @PostMapping("")
    @Override
    public ResponseEntity<PromotionResponse> createPromotion(@ModelAttribute PromotionRequest request) {
        return ResponseEntity.ok(promotionService.createPromotion(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponse> updatePromotion(
            @PathVariable UUID id,
            @ModelAttribute PromotionRequest request) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, request));
    }

    @PostMapping("/{id}/items")
    @Override
    public ResponseEntity<PromotionResponse> addItems(@PathVariable UUID id,
                                                      @RequestBody List<PromotionItemRequest> items) {
        return ResponseEntity.ok(promotionService.addItems(id, items));
    }

    @PostMapping("/{id}/rules")
    public ResponseEntity<PromotionResponse> addRules(
            @PathVariable UUID id,
            @RequestBody List<PromotionRuleRequest> rules) {
        PromotionResponse response = new PromotionResponse();
        try {
            // Thêm rule vào promotion
            response = promotionService.addRules(id, rules);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Lỗi do logic nghiệp vụ
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Lỗi server
            response.setErrorMessage("Server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletePromotion(@PathVariable UUID id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/rules/{ruleId}")
    @Override
    public ResponseEntity<PromotionResponse> deleteRule(@PathVariable UUID ruleId) {
        PromotionResponse updatedPromotion = promotionService.deleteRule(ruleId); // Service trả về promotion sau khi xóa rule
        return ResponseEntity.ok(updatedPromotion);
    }
}
