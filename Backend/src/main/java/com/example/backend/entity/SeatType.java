package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "seat_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID id;

    @Column(name = "name", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String name;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default 0")
    private boolean isDeleted = false;

    // QUAN HỆ 1-N VỚI SEAT
    @OneToMany(mappedBy = "seatType", fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();
}
