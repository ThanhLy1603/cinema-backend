package com.example.backend.repository;

import com.example.backend.entity.PriceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceTicketRepository extends JpaRepository<PriceTicket, UUID> {
    boolean existsByFilmIdAndSeatTypeIdAndShowTimeIdAndDayTypeAndStartDate(
            UUID filmId, UUID seatTypeId, UUID showTimeId, String dayType, LocalDate startDate
    );
    long countByStartDate(LocalDate startDate);

    Optional<PriceTicket> findTopByFilmIdAndSeatTypeId(UUID filmId, UUID seatTypeId);

    @Query("SELECT pt FROM PriceTicket pt WHERE pt.film.id = :filmId AND pt.seatType.id = :seatTypeId AND pt.showTime.id = :showTimeId " +
            "AND pt.dayType = :dayType AND pt.startDate <= :scheduleDate AND pt.endDate IS NULL")
    Optional<PriceTicket> findTicketPrice(UUID filmId, UUID seatTypeId, UUID showTimeId, PriceTicket.DayType dayType, LocalDate scheduleDate);
}
