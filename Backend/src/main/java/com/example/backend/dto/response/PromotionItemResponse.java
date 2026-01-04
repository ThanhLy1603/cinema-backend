package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionItemResponse {
    private UUID id;
    private UUID productId;
    private UUID filmId;
    private UUID seatTypeId;
    private String note;
}
