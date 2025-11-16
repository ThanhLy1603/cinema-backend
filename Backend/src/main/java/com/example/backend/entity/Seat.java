package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "seats"
//        , uniqueConstraints = {
//        @UniqueConstraint(name = "UQ_SeatPositionInRoom", columnNames = {"room_id", "position"})
//}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UNIQUEIDENTIFIER", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id", nullable = false)
    private SeatType seatType;

    @Column(name = "position", length = 10, nullable = false, columnDefinition = "NVARCHAR(10)")
    private String position;

    @Column(name = "active", nullable = false, columnDefinition = "BIT default 1")
    private boolean active = true;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default 0")
    private boolean isDeleted = false;
}
