package com.example.backend.restapi;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.PriceTicketManageRequest;
import com.example.backend.dto.response.PriceTicketManageResponse;
import com.example.backend.service.PriceTicketManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/price-tickets")
@RequiredArgsConstructor
public class PriceTicketManageRestApi {
    private final PriceTicketManageService priceTicketManageService;

    // Lấy toàn bộ giá vé (dùng cho danh sách + filter frontend)
    @GetMapping
    public ResponseEntity<List<PriceTicketManageResponse>> getAllPriceTickets() {
        return ResponseEntity.ok(priceTicketManageService.getAllPriceTickets());
    }

    // Thêm nhiều giá vé cùng lúc (dùng cho grid thêm nhanh - giống lịch chiếu)
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse> bulkCreate(@RequestBody List<PriceTicketManageRequest> requests) {
        ApiResponse response = priceTicketManageService.bulkPriceTickets(requests);
        return ResponseEntity.ok(response);
    }

    // Cập nhật giá vé (sửa đơn)
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePriceTicket(
            @PathVariable UUID id,
            @RequestBody PriceTicketManageRequest request) {
        ApiResponse response = priceTicketManageService.updatePriceTicket(id, request);
        return ResponseEntity.ok(response);
    }

    // Xóa giá vé (soft delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePriceTicket(@PathVariable UUID id) {
        ApiResponse response = priceTicketManageService.deletePriceTicket(id);
        return ResponseEntity.ok(response);
    }
}