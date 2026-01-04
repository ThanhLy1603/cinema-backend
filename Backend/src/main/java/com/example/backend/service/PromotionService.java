package com.example.backend.service;

import com.example.backend.dto.request.PromotionItemRequest;
import com.example.backend.dto.request.PromotionRequest;
import com.example.backend.dto.request.PromotionRuleRequest;
import com.example.backend.dto.response.PromotionItemResponse;
import com.example.backend.dto.response.PromotionResponse;
import com.example.backend.dto.response.PromotionRuleResponse;
import com.example.backend.entity.*;
import com.example.backend.enums.PromotionRuleType;
import com.example.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    // ================== GET ==================
    @Transactional(readOnly = true)
    public List<PromotionResponse> getAllPromotions() {
        return promotionRepository.findAllByIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PromotionResponse getPromotion(UUID id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return mapToResponse(promotion);
    }

    // ================== CREATE / UPDATE ==================
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = buildPromotionEntity(request);
        promotionRepository.save(promotion);
        return mapToResponse(promotion);
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

        if (request.getPosterFile() != null && !request.getPosterFile().isEmpty()) {
            promo.setPoster(uploadPoster(request.getPosterFile()));
        }

        promotionRepository.save(promo);
        return mapToResponse(promo);
    }

    // ================== ITEMS ==================
    @Transactional
    public PromotionResponse addItems(UUID promotionId, List<PromotionItemRequest> items) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        // Lấy tất cả entity trước để giảm query trong loop
        Map<UUID, Product> productMap = productRepository.findAllById(
                items.stream().map(PromotionItemRequest::getProductId).filter(Objects::nonNull).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Product::getId, p -> p));

        Map<UUID, Film> filmMap = filmRepository.findAllById(
                items.stream().map(PromotionItemRequest::getFilmId).filter(Objects::nonNull).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Film::getId, f -> f));

        Map<UUID, SeatType> seatMap = seatTypeRepository.findAllById(
                items.stream().map(PromotionItemRequest::getSeatTypeId).filter(Objects::nonNull).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(SeatType::getId, s -> s));

        List<PromotionItem> itemEntities = items.stream().map(itemReq -> PromotionItem.builder()
                        .promotion(promotion)
                        .product(itemReq.getProductId() != null ? productMap.get(itemReq.getProductId()) : null)
                        .film(itemReq.getFilmId() != null ? filmMap.get(itemReq.getFilmId()) : null)
                        .seatType(itemReq.getSeatTypeId() != null ? seatMap.get(itemReq.getSeatTypeId()) : null)
                        .note(itemReq.getNote())
                        .build())
                .collect(Collectors.toList());

        promotionItemRepository.saveAll(itemEntities); // batch save
        promotion.getItems().addAll(itemEntities);

        return mapToResponse(promotion);
    }

    // ================== RULES ==================
    @Transactional
    public PromotionResponse addRules(UUID promotionId, List<PromotionRuleRequest> rules) throws Exception {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        if (!promotion.getRules().isEmpty()) {
            throw new RuntimeException("Chương trình '" + promotion.getName() + "' chỉ được có 1 rule");
        }

        List<PromotionRule> ruleEntities = new ArrayList<>();
        for (PromotionRuleRequest ruleReq : rules) {
            PromotionRuleType ruleType;
            try {
                ruleType = PromotionRuleType.valueOf(ruleReq.getRuleType());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid ruleType: " + ruleReq.getRuleType());
            }

            PromotionRule rule = PromotionRule.builder()
                    .promotion(promotion)
                    .ruleType(ruleType.name())
                    .ruleValue(objectMapper.writeValueAsString(ruleReq.getRuleValue()))
                    .build();

            ruleEntities.add(rule);
        }

        promotionRuleRepository.saveAll(ruleEntities); // batch save
        promotion.getRules().addAll(ruleEntities);

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
        return mapToResponse(promo);
    }

    // ================== DELETE PROMOTION ==================
    @Transactional
    public void deletePromotion(UUID promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        promotion.setDeleted(true);
        promotion.setActive(false);
        promotionRepository.save(promotion);
    }

    // ================== PRIVATE HELPERS ==================
    private Promotion buildPromotionEntity(PromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .description(request.getDescription())
                .discountPercent(request.getDiscountPercent())
                .discountAmount(request.getDiscountAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(true)
                .isDeleted(false)
                .poster(request.getPosterFile() != null ? uploadPoster(request.getPosterFile()) : null)
                .build();
        return promotion;
    }

    private String uploadPoster(MultipartFile posterFile) {
        try {
            return fileStorageService.saveFile(posterFile);
        } catch (Exception e) {
            throw new RuntimeException("Upload poster thất bại");
        }
    }

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
}
