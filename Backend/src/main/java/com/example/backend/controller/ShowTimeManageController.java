package com.example.backend.controller;

import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/showtimes")
public interface ShowTimeManageController {

    @GetMapping
    ResponseEntity<List<ShowTimeManageResponse>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ShowTimeManageResponse> getById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<ShowTimeManageResponse> create(@RequestBody ShowTimeManageRequest request);

    @PutMapping("/{id}")
    ResponseEntity<ShowTimeManageResponse> update(@PathVariable UUID id, @RequestBody ShowTimeManageRequest request);

    @PatchMapping("/{id}/status")
    ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestParam boolean isDeleted);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
