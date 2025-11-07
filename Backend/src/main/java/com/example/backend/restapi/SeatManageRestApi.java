package com.example.backend.restapi;

import com.example.backend.controller.SeatManageController;
import com.example.backend.dto.SeatManageResponse;
import com.example.backend.service.SeatManageService;
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

    @Override
    @GetMapping("")
    public ResponseEntity<List<SeatManageResponse>> getAllSeats() {
        return ResponseEntity.ok(seatManageService.getAllSeats());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsByRoomId(@PathVariable UUID roomId) {
        return null;
    }
}
