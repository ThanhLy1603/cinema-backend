package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SeatReservationResponse {
    private UUID seatId;
    private String position;
    private String seatType;          // ví dụ: “Ghế Thường”, “Ghế VIP”
    private String status;            // available | holding | booked
    private String holderId;          // nếu status = holding (và có thể nếu booked)
    private LocalDateTime holdExpiresAt;
}
