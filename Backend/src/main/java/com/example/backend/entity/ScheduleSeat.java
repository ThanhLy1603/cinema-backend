package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedule_seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleSeat {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false, columnDefinition = "uniqueidentifier")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false, columnDefinition = "uniqueidentifier")
    private Seat seat;

    @Column(nullable = false, length = 20)
    private String status = "available"; // available | holding | booked

    private String holderId; // nullable

    private LocalDateTime holdExpiresAt; // nullable

    private boolean isDeleted = false;
}
