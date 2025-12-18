package com.example.backend.controller;

import com.example.backend.dto.request.PromotionItemRequest;
import com.example.backend.dto.request.PromotionRequest;
import com.example.backend.dto.response.PromotionResponse;
import com.example.backend.dto.request.PromotionRuleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface PromotionController {

    ResponseEntity<List<PromotionResponse>> getAllPromotions();

    ResponseEntity<PromotionResponse> getPromotionById(@PathVariable UUID id);

    ResponseEntity<PromotionResponse> createPromotion(@RequestBody PromotionRequest request);

    ResponseEntity<PromotionResponse> updatePromotion(@PathVariable UUID id, @ModelAttribute PromotionRequest request);

    ResponseEntity<PromotionResponse> addItems(@PathVariable UUID id, @RequestBody List<PromotionItemRequest> items);

    ResponseEntity<PromotionResponse> addRules(@PathVariable UUID id, @RequestBody List<PromotionRuleRequest> rules) throws Exception;

    ResponseEntity<Void> deletePromotion(@PathVariable UUID id);

    ResponseEntity<PromotionResponse> deleteRule(@PathVariable UUID ruleId);
}
