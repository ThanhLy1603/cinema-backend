package com.example.backend.restapi;

import com.example.backend.controller.PromotionController;
import com.example.backend.dto.*;
import com.example.backend.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/promotions")
@RequiredArgsConstructor
public class PromotionRestApi implements PromotionController {

    private final PromotionService promotionService;

    // ================== GET ==================
    @GetMapping
    @Override
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable UUID id) {
        return ResponseEntity.ok(promotionService.getPromotion(id));
    }

    // ================== CREATE / UPDATE ==================
    @PostMapping
    @Override
    public ResponseEntity<PromotionResponse> createPromotion(@ModelAttribute PromotionRequest request) {
        PromotionResponse response = promotionService.createPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<PromotionResponse> updatePromotion(
            @PathVariable UUID id,
            @ModelAttribute PromotionRequest request
    ) {
        PromotionResponse response = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(response);
    }

    // ================== ITEMS ==================
    @PostMapping("/{id}/items")
    @Override
    public ResponseEntity<PromotionResponse> addItems(
            @PathVariable UUID id,
            @RequestBody List<PromotionItemRequest> items
    ) {
        PromotionResponse response = promotionService.addItems(id, items);
        return ResponseEntity.ok(response);
    }

    // ================== RULES ==================
    @PostMapping("/{id}/rules")
    public ResponseEntity<PromotionResponse> addRules(
            @PathVariable UUID id,
            @RequestBody List<PromotionRuleRequest> rules
    ) {
        try {
            PromotionResponse response = promotionService.addRules(id, rules);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            PromotionResponse errorResponse = new PromotionResponse();
            errorResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            PromotionResponse errorResponse = new PromotionResponse();
            errorResponse.setErrorMessage("Server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/rules/{ruleId}")
    @Override
    public ResponseEntity<PromotionResponse> deleteRule(@PathVariable UUID ruleId) {
        PromotionResponse updatedPromotion = promotionService.deleteRule(ruleId);
        return ResponseEntity.ok(updatedPromotion);
    }

    // ================== DELETE ==================
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletePromotion(@PathVariable UUID id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}
