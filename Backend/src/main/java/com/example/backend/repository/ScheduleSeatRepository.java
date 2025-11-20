package com.example.backend.repository;

import com.example.backend.entity.Schedule;
import com.example.backend.entity.ScheduleSeat;
import com.example.backend.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleSeatRepository extends CrudRepository<ScheduleSeat, UUID> {
    List<ScheduleSeat> findByScheduleIdOrderBySeat_PositionAsc(UUID scheduleId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ScheduleSeat ss WHERE ss.schedule.id = :scheduleId AND ss.seat.id = :seatId")
    Optional<ScheduleSeat> findByScheduleIdAndSeatIdForUpdate(@Param("scheduleId") UUID scheduleId,
                                                              @Param("seatId") UUID seatId);
    Optional<ScheduleSeat> findByScheduleAndSeat(Schedule schedule, Seat seat);
    boolean existsByScheduleAndSeat(Schedule schedule, Seat seat);
    @Modifying
    @Query("UPDATE ScheduleSeat ss SET ss.status = 'available', ss.holderId = NULL, ss.holdExpiresAt = NULL " +
            "WHERE ss.status = 'holding' AND ss.holdExpiresAt IS NOT NULL AND ss.holdExpiresAt <= :now")
    int releaseExpiredHolds(@Param("now") LocalDateTime now);

    @Query("SELECT ss FROM ScheduleSeat ss JOIN ss.seat s WHERE ss.schedule.id = :scheduleId ORDER BY s.position ASC")
    List<ScheduleSeat> findByScheduleIdOrderBySeatPosition(@Param("scheduleId") UUID scheduleId);
}
