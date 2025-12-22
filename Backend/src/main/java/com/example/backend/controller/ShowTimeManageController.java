package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ShowTimeManageRequest;
import com.example.backend.dto.response.ShowTimeManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ShowTimeManageController {
    ResponseEntity<List<ShowTimeManageResponse>> getAll();
    ResponseEntity<ApiResponse> create(@RequestBody ShowTimeManageRequest request);
    ResponseEntity<ApiResponse> delete(@PathVariable UUID id);
}
