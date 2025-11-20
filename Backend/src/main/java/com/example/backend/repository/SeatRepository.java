package com.example.backend.repository;

import com.example.backend.entity.Seat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findAllByIsDeletedFalse();
    List<Seat> findAllByIsDeletedFalseAndRoomId(UUID roomId);
    List<Seat> findAllByIsDeletedFalseAndSeatTypeId(UUID seatTypeId);
    List<Seat> findAllByIsDeletedFalseAndRoomIdAndSeatTypeId(UUID roomId, UUID seatTypeId);

    @Query("SELECT st FROM Seat st WHERE st.room.id = :roomId AND st.isDeleted = false " +
            "AND NOT EXISTS (SELECT it FROM InvoiceTicket it WHERE it.seat.id = st.id AND it.schedule.id = :scheduleId)")
    List<Seat> findTop3AvailableSeats(@Param("roomId") UUID roomId, @Param("scheduleId") UUID scheduleId, PageRequest pageable);

    @Query("SELECT st FROM Seat st JOIN st.seatType stt WHERE st.room.id = :roomId AND stt.name = 'Ghế VIP' AND st.isDeleted = false " +
            "AND NOT EXISTS (SELECT it FROM InvoiceTicket it WHERE it.seat.id = st.id AND it.schedule.id = :scheduleId)")
    Optional<Seat> findTopVipSeatAvailable(@Param("roomId") UUID roomId, @Param("scheduleId") UUID scheduleId);

    List<Seat> findByRoomIdAndIsDeletedFalseOrderByPositionAsc(UUID roomId);
}
