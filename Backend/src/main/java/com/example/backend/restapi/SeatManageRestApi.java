package com.example.backend.restapi;

import com.example.backend.controller.SeatManageController;
import com.example.backend.dto.RoomManageResponse;
import com.example.backend.dto.SeatManageResponse;
import com.example.backend.dto.SeatTypeManageResponse;
import com.example.backend.service.RoomManageService;
import com.example.backend.service.SeatManageService;
import com.example.backend.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
