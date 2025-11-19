package com.example.backend.repository;

import com.example.backend.entity.Film;
import com.example.backend.entity.Room;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ShowTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByIsDeletedFalse();

    List<Schedule> findByIsDeletedFalseOrderByScheduleDateDescShowTimeStartTimeAsc();

    boolean existsByRoomAndShowTimeAndScheduleDate(
            Room room, ShowTime showTime, LocalDate date
    );

    List<Schedule> findByRoomAndScheduleDateAndIsDeletedFalse(Room room, LocalDate date);

    List<Schedule> findByFilmAndIsDeletedFalse(
            Film film
    );

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.isDeleted = false AND EXISTS (" +
            "   SELECT st FROM Seat st " +
            "   WHERE st.room = s.room AND st.isDeleted = false " +
            "   AND NOT EXISTS (" +
            "       SELECT it FROM InvoiceTicket it " +
            "       WHERE it.seat = st AND it.schedule = s" +
            "   )" +
            ") ORDER BY s.scheduleDate DESC, s.id DESC")
    List<Schedule> findTopAvailableSchedule(Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s " +
            "WHERE s.room = :room " +
            "AND s.scheduleDate = :date " +
            "AND s.showTime = :showTime " +
            "AND s.isDeleted = false")
    boolean existsByRoomAndScheduleDateAndShowTime(
            @Param("room") Room room,
            @Param("date") LocalDate date,
            @Param("showTime") ShowTime showTime);
}