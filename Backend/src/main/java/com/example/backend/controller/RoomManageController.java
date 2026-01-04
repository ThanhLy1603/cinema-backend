package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.RoomManageResponse;
import com.example.backend.dto.request.RoomMangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface RoomManageController {
    public ResponseEntity<List<RoomManageResponse>> getAll();
    public ResponseEntity<RoomManageResponse> getById(@PathVariable UUID id);
    public ResponseEntity<ApiResponse> create(@RequestBody RoomMangeRequest request);
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody RoomMangeRequest request);
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id);
}
