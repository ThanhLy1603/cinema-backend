package com.example.backend.repository;

import com.example.backend.entity.Seat;
import com.example.backend.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findAllByIsDeletedFalse();
    List<Seat> findAllByIsDeletedFalseAndRoomId(UUID roomId);
    List<Seat> findAllByIsDeletedFalseAndSeatTypeId(UUID seatTypeId);
    List<Seat> findAllByIsDeletedFalseAndRoomIdAndSeatTypeId(UUID roomId, UUID seatTypeId);
}
