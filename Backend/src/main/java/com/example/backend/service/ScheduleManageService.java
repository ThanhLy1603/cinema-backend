package com.example.backend.service;

import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.RoomManageResponse;
import com.example.backend.dto.ScheduleManageResponse;
import com.example.backend.dto.ShowTimeManageResponse;
import com.example.backend.entity.Film;
import com.example.backend.entity.Room;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ShowTime;
import com.example.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleManageService {
    private final ScheduleRepository scheduleRepository;

    public List<ScheduleManageResponse> getAllSchedules() {
        List<ScheduleManageResponse> schedules = scheduleRepository.findByIsDeletedFalseOrderByScheduleDateDescShowTimeStartTimeAsc()
                .stream()
                .map(this::toScheduleManageResponse)
                .collect(Collectors.toList());

        return schedules;
    }

    private ScheduleManageResponse toScheduleManageResponse(Schedule schedule) {
        return new ScheduleManageResponse(
                schedule.getId(),
                toFilmResponse(schedule.getFilm()),
                toRoomResponse(schedule.getRoom()),
                toShowTimeResponse(schedule.getShowTime()),
                schedule.getScheduleDate()
        );
    }

    private ShowTimeManageResponse toShowTimeResponse(ShowTime showTime) {
        return new ShowTimeManageResponse(
                showTime.getId(),
                showTime.getStartTime()
        );
    }

    private FilmResponse toFilmResponse(Film film) {
        return new FilmResponse(
                film.getId(),
                film.getName(),
                film.getCountry(),
                film.getDirector(),
                film.getActor(),
                film.getDescription(),
                film.getDuration(),
                film.getPoster(),
                film.getTrailer(),
                film.getReleaseDate(),
                film.getStatus()
        );
    }

    private RoomManageResponse toRoomResponse(Room room) {
        return new RoomManageResponse(room.getId(), room.getName(), room.getStatus().name());
    }
}
