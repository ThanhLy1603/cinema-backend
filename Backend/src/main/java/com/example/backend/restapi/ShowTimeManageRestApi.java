package com.example.backend.restapi;

import com.example.backend.controller.ShowTimeManageController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import com.example.backend.service.ShowTimeManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/show-times")
@RequiredArgsConstructor
public class ShowTimeManageRestApi implements ShowTimeManageController {

    private final ShowTimeManageService service;

    @GetMapping("")
    public ResponseEntity<List<ShowTimeManageResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody ShowTimeManageRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
