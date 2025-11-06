package com.example.backend.controller;

import com.example.backend.dto.SeatManageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SeatManageController {
    public ResponseEntity<List<SeatManageResponse>> getAllSeats();
}
