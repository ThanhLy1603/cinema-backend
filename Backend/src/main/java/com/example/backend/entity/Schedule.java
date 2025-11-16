package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "schedules"
//        , uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "UQ_Schedule",
//                        columnNames = {"room_id", "show_time_id", "schedule_date"}
//                )
//        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "film_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Schedule_Film")
    )
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "room_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Schedule_Room")
    )
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "show_time_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Schedule_ShowTime")
    )
    private ShowTime showTime;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", film=" + (film != null ? film.getName() : "null") +
                ", room=" + (room != null ? room.getName() : "null") +
                ", showTime=" + (showTime != null ? showTime.getStartTime() : "null") +
                ", scheduleDate=" + scheduleDate +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
