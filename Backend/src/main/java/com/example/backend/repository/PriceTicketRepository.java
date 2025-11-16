package com.example.backend.repository;

import com.example.backend.entity.PriceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PriceTicketRepository extends JpaRepository<PriceTicket, UUID> {
    boolean existsByFilmIdAndSeatTypeIdAndShowTimeIdAndDayTypeAndStartDate(
            UUID filmId, UUID seatTypeId, UUID showTimeId, String dayType, LocalDate startDate
    );
    long countByStartDate(LocalDate startDate);
}
