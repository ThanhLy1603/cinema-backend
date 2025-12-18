package com.example.backend.restapi;

import com.example.backend.controller.RoomManageController;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.RoomMangeRequest;
import com.example.backend.dto.response.RoomManageResponse;
import com.example.backend.service.RoomManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/rooms")
@RequiredArgsConstructor
public class RoomManageRestApi implements RoomManageController {
    private final RoomManageService roomManageService;

    // GET all rooms
    @Override
    @GetMapping("")
    public ResponseEntity<List<RoomManageResponse>> getAll() {
        return ResponseEntity.ok(roomManageService.getAllRooms());
    }

    // GET by id (optional)
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RoomManageResponse> getById(@PathVariable UUID id) {
        // Could implement service.getById
        return ResponseEntity.ok(roomManageService.getAllRooms()
                .stream()
                .filter(r -> r.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Room not found")));
    }

    // POST create
    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody RoomMangeRequest request) {
        return ResponseEntity.ok(roomManageService.createRoom(request));
    }

    // PUT update
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody RoomMangeRequest request) {
        return ResponseEntity.ok(roomManageService.updateRoom(id, request));
    }

    // DELETE (soft)
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(roomManageService.deleteRoom(id));
    }
}
