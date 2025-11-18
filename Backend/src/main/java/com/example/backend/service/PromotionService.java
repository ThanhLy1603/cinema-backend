package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.*;
import com.example.backend.enums.PromotionRuleType;
import com.example.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionItemRepository promotionItemRepository;
    private final PromotionRuleRepository promotionRuleRepository;
    private final ProductRepository productRepository;
    private final FilmRepository filmRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final FileStorageService fileStorageService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    // --- Lấy danh sách promotions ---
    @Transactional
    public List<PromotionResponse> getAllPromotions() {
        List<Promotion> list = promotionRepository.findAll()
                .stream()
                .filter(p -> !p.isDeleted()) // chỉ lấy những promotion chưa bị xóa
                .collect(Collectors.toList());

        return list.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- Lấy promotion theo ID ---
    public PromotionResponse getPromotion(UUID id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return mapToResponse(promotion);
    }

    // --- Tạo Promotion mới (chưa có items/rules) ---
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .description(request.getDescription())
                .discountPercent(request.getDiscountPercent())
                .discountAmount(request.getDiscountAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(true)
                .isDeleted(false)
                .build();

        //Xử lý upload poster
        if (request.getPosterFile() != null && !request.getPosterFile().isEmpty()) {
            try {
                String savedName = fileStorageService.saveFile(request.getPosterFile());
                promotion.setPoster(savedName);
            } catch (Exception e) {
                throw new RuntimeException("Upload poster thất bại");
            }
        } else {
            promotion.setPoster(null);
        }

        promotionRepository.save(promotion);
        return mapToResponse(promotion);
    }

    // --- Thêm items vào promotion ---
    @Transactional
    public PromotionResponse addItems(UUID promotionId, List<PromotionItemRequest> items) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        for (PromotionItemRequest itemReq : items) {
            PromotionItem item = PromotionItem.builder()
                    .promotion(promotion)
                    .product(itemReq.getProductId() != null ? productRepository.findById(itemReq.getProductId()).orElse(null) : null)
                    .film(itemReq.getFilmId() != null ? filmRepository.findById(itemReq.getFilmId()).orElse(null) : null)
                    .seatType(itemReq.getSeatTypeId() != null ? seatTypeRepository.findById(itemReq.getSeatTypeId()).orElse(null) : null)
                    .note(itemReq.getNote())
                    .build();
            promotionItemRepository.save(item);
        }

        return mapToResponse(promotion);
    }

    // --- Thêm rules vào promotion ---
    @Transactional
    public PromotionResponse addRules(UUID promotionId, List<PromotionRuleRequest> rules) throws Exception {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        //Check promotion đã có rule chưa
        if (!promotion.getRules().isEmpty()) {
            throw new RuntimeException("Chương trình '" + promotion.getName() + "' chỉ được có 1 rule");
        }
        for (PromotionRuleRequest ruleReq : rules) {
            // Chỉ validate enum hợp lệ
            PromotionRuleType ruleType;
            try {
                ruleType = PromotionRuleType.valueOf(ruleReq.getRuleType());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid ruleType: " + ruleReq.getRuleType());
            }
            // Tạo rule
            PromotionRule rule = PromotionRule.builder()
                    .promotion(promotion)
                    .ruleType(ruleType.name())
                    .ruleValue(new ObjectMapper().writeValueAsString(ruleReq.getRuleValue()))
                    .build();
            // Lưu rule
            promotionRuleRepository.save(rule);
            // Thêm vào danh sách của promotion
            promotion.getRules().add(rule);
        }

        return mapToResponse(promotion);
    }


    @Transactional
    public PromotionResponse deleteRule(UUID ruleId) {
        PromotionRule rule = promotionRuleRepository.findById(ruleId)
                .orElseThrow(() -> new RuntimeException("Rule không tồn tại"));
        Promotion promo = rule.getPromotion();
        if (promo != null) {
            promo.getRules().remove(rule);
            promotionRepository.save(promo);
        }
        promotionRuleRepository.delete(rule);
        // Trả về promotion đã cập nhật
        return mapToResponse(promo);  // mapToResponse là method map Promotion → PromotionResponse
    }

    @Transactional
    public PromotionResponse updatePromotion(UUID id, PromotionRequest request) {
        Promotion promo = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setStartDate(request.getStartDate());
        promo.setEndDate(request.getEndDate());
        promo.setDiscountPercent(request.getDiscountPercent());
        promo.setDiscountAmount(request.getDiscountAmount());

        if (request.getPosterFile() != null) {
            // logic lưu file và set poster
        }
        promotionRepository.save(promo);
        return mapToResponse(promo);
    }

    // --- Xóa promotion ---
    @Transactional
    public void deletePromotion(UUID promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setDeleted(true);
        promotion.setActive(false);
        promotionRepository.save(promotion);
    }

    // --- Mapping Entity → Response DTO ---
    private PromotionResponse mapToResponse(Promotion promotion) {
        List<PromotionItemResponse> itemResponses = promotion.getItems().stream()
                .map(item -> PromotionItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                        .filmId(item.getFilm() != null ? item.getFilm().getId() : null)
                        .seatTypeId(item.getSeatType() != null ? item.getSeatType().getId() : null)
                        .note(item.getNote())
                        .build())
                .collect(Collectors.toList());

        List<PromotionRuleResponse> ruleResponses = promotion.getRules().stream()
                .map(rule -> PromotionRuleResponse.builder()
                        .id(rule.getId())
                        .ruleType(rule.getRuleType())
                        .ruleValue(rule.getRuleValue())
                        .build())
                .collect(Collectors.toList());

        return PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .poster(promotion.getPoster())
                .discountPercent(promotion.getDiscountPercent())
                .discountAmount(promotion.getDiscountAmount())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .active(promotion.isActive())
                .items(itemResponses)
                .rules(ruleResponses)
                .build();
    }
    private PromotionRuleType inferRuleType(Promotion promo) {
        // Nếu có discountPercent > 0 → PERCENT
        if (promo.getDiscountPercent() != null && promo.getDiscountPercent().compareTo(BigDecimal.ZERO) > 0) {
            return PromotionRuleType.PERCENT;
        }

        // Nếu có nhiều item, là combo → FIXED_COMBO
        if (!promo.getItems().isEmpty()) {
            return PromotionRuleType.FIXED_COMBO;
        }

        // Nếu có luật BUY_X_GET_Y → kiểm tra trong rules hoặc item đặc biệt
        if (promo.getRules() != null) {
            for (PromotionRule r : promo.getRules()) {
                if ("BUY_X_GET_Y".equalsIgnoreCase(r.getRuleType())) {
                    return PromotionRuleType.BUY_X_GET_Y;
                }
            }
        }

        // Nếu có TOTAL_PERCENT → kiểm tra discountPercent áp dụng tổng hóa đơn
        if (promo.getDiscountPercent() != null && promo.getDiscountPercent().compareTo(BigDecimal.ZERO) > 0 && promo.getItems().isEmpty()) {
            return PromotionRuleType.TOTAL_PERCENT;
        }

        throw new RuntimeException("Không xác định được loại rule cho promotion: " + promo.getName());
    }
}
