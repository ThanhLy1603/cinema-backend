package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "price_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @JsonIgnore
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id", nullable = false)
    @JsonIgnore
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_time_id", nullable = false)
    @JsonIgnore
    private ShowTime showTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_type", length = 20, nullable = false)
    private DayType dayType;

    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    public enum DayType {
        WEEKDAY,
        WEEKEND,
        HOLIDAY,
        SPECIAL
    }
}
