package com.example.backend.restapi;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.RoomRequest;
import com.example.backend.dto.RoomResponse;
import com.example.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/rooms")
@RequiredArgsConstructor
public class RoomManageRestApi {
    private final RoomService roomService;

    // GET all rooms
    @GetMapping("")
    public ResponseEntity<List<RoomResponse>> getAll() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // GET by id (optional)
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
        // Could implement service.getById
        return ResponseEntity.ok(roomService.getAllRooms()
                .stream()
                .filter(r -> r.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Room not found")));
    }

    // POST create
    @PostMapping("")
    public ResponseEntity<RoomResponse> create(@Validated @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    // PUT update
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable UUID id, @Validated @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    // DELETE (soft)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(roomService.deleteRoom(id));
    }
}
