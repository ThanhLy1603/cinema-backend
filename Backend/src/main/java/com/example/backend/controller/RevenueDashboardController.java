package com.example.backend.controller;

import com.example.backend.dto.response.RevenueDashboardResponse;
import org.springframework.http.ResponseEntity;

public interface RevenueDashboardController {
    public ResponseEntity<RevenueDashboardResponse> getRevenueDashboard();
}
