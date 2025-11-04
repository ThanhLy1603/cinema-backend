package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ShowTimeManageController {
    ResponseEntity<List<ShowTimeManageResponse>> getAll();
    ResponseEntity<ApiResponse> create(@RequestBody ShowTimeManageRequest request);
    ResponseEntity<ApiResponse> delete(@PathVariable UUID id);
}
