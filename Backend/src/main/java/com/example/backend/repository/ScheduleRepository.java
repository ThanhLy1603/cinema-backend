package com.example.backend.repository;

import com.example.backend.entity.Film;
import com.example.backend.entity.Room;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
