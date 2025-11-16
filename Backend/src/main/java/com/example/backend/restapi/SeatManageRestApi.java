package com.example.backend.restapi;

import com.example.backend.controller.SeatManageController;
import com.example.backend.dto.*;
import com.example.backend.service.RoomManageService;
import com.example.backend.service.SeatManageService;
import com.example.backend.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/seats")
@RequiredArgsConstructor
public class SeatManageRestApi implements SeatManageController {

    private final SeatManageService seatManageService;
    private final RoomManageService roomManageService;
    private final SeatTypeService seatTypeService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<SeatManageResponse>> getAllSeats() {
        return ResponseEntity.ok(seatManageService.getAllSeats());
    }

    @Override
    @GetMapping("/room/{roomId}/seat-type/{seatTypeId}")
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsRoomIdAndSeatTypeId(
            @PathVariable UUID roomId,
            @PathVariable UUID seatTypeId) {
        return ResponseEntity.ok(seatManageService.getSeatsByRoomIdAndTypeId(roomId, seatTypeId));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomManageResponse>> getAllRooms() {
        return ResponseEntity.ok(roomManageService.getAllRooms());
    }

    @GetMapping("/seat-types")
    public ResponseEntity<List<SeatTypeManageResponse>> getAllSeatTypes() {
        return ResponseEntity.ok(seatTypeService.getAllSeatTypes());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsByRoomId(@PathVariable UUID roomId) {
        return ResponseEntity.ok(seatManageService.getSeatsByRoomId(roomId));
    }

    @GetMapping("/seat-type/{seatTypeId}")
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsBySeatTypeId(@PathVariable UUID seatTypeId) {
        return ResponseEntity.ok(seatManageService.getSeatsBySeatTypeId(seatTypeId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSeat(@PathVariable UUID id, @RequestBody SeatManageRequest request) {
        return ResponseEntity.ok(seatManageService.updateSeat(id, request));
    }
}
