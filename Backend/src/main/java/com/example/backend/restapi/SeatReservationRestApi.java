package com.example.backend.restapi;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.CheckoutRequest;
import com.example.backend.dto.request.HoldRequest;
import com.example.backend.dto.response.SeatReservationResponse;
import com.example.backend.service.SeatReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/reserve-seat")
public class SeatReservationRestApi {
    private final SeatReservationService seatReservationService;

    @GetMapping("/{scheduleId}/seats")
    public ResponseEntity<List<SeatReservationResponse>> getSeats(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(seatReservationService.getSeats(scheduleId));
    }

    @PutMapping("/{scheduleId}/seats/{seatId}/hold")
    public ResponseEntity<ApiResponse> holdSeat(@PathVariable UUID scheduleId,
                                                @PathVariable UUID seatId,
                                                @RequestBody HoldRequest request) {
        return ResponseEntity.ok(seatReservationService.holdSeat(scheduleId, seatId, request));
    }

    @PutMapping("/{scheduleId}/seats/{seatId}/release")
    public ResponseEntity<?> releaseSeat(
            @PathVariable UUID scheduleId,
            @PathVariable UUID seatId,
            @RequestBody Map<String, String> body
    ) {
        try {
            seatReservationService.releaseSeat(scheduleId, seatId, body.get("holderId"));
            return ResponseEntity.ok(Map.of("message", "released"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{scheduleId}/checkout")
    public ResponseEntity<?> checkout(
            @PathVariable UUID scheduleId,
            @RequestBody CheckoutRequest request
    ) {
        try {
            return ResponseEntity.ok(seatReservationService.checkout(scheduleId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
