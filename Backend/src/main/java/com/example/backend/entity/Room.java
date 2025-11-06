package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID id;

    @Column(name = "name", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false, columnDefinition = "NVARCHAR(20) default 'active'")
    private RoomStatus status = RoomStatus.active;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default 0")
    private boolean isDeleted = false;

    // QUAN HỆ 1-N VỚI SEAT
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Schedule> schedules;

    public enum RoomStatus {
        active, closed, maintenance
    }
}
