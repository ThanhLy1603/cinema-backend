package com.example.backend.controller;

import com.example.backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface SeatManageController {
    public ResponseEntity<List<SeatManageResponse>> getAllSeats();
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsRoomIdAndSeatTypeId(UUID roomId, UUID seatTypeId);
    public ResponseEntity<List<RoomManageResponse>> getAllRooms();
    public ResponseEntity<List<SeatTypeManageResponse>> getAllSeatTypes();
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsByRoomId(@PathVariable UUID roomId);
    public ResponseEntity<List<SeatManageResponse>> getAllSeatsBySeatTypeId(@PathVariable UUID seatTypeId);
    public ResponseEntity<ApiResponse> updateSeat(@PathVariable UUID id, @RequestBody SeatManageRequest request);
}
