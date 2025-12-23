package com.example.backend.restapi;

import com.example.backend.controller.RevenueDashboardController;
import com.example.backend.dto.response.RevenueByDateResponse;
import com.example.backend.dto.response.RevenueDashboardResponse;
import com.example.backend.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/revenue")
public class RevenueDashboardRestApi implements RevenueDashboardController {
    private final RevenueService revenueService;


    @GetMapping("")
    public ResponseEntity<RevenueDashboardResponse> getRevenueDashboard() {
        return ResponseEntity.ok(revenueService.getRevenueDashboard());
    }

    @GetMapping("/daily")
    public List<RevenueByDateResponse> dailyRevenue() {
        return revenueService.getDailyRevenue();
    }
}
