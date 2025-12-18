package com.example.backend.service;

import com.example.backend.dto.response.FilmResponse;
import com.example.backend.dto.response.RoomManageResponse;
import com.example.backend.dto.response.ScheduleManageResponse;
import com.example.backend.dto.response.ShowTimeManageResponse;
import com.example.backend.entity.Film;
import com.example.backend.entity.Room;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ShowTime;
import com.example.backend.repository.FilmRepository;
import com.example.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final FilmRepository filmRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public List<ScheduleManageResponse> getSchedulesByFilmId(UUID filmId) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) return List.of();

        List<ScheduleManageResponse> schedules = scheduleRepository
                .findByFilmAndIsDeletedFalse(film)
                .stream()
                .map(this::toScheduleManageResponse)
                .collect(Collectors.toList());

        return schedules;
    }

    @Transactional
    public ScheduleManageResponse getScheduleById(UUID id) {
        if (id == null) return null;

        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        ScheduleManageResponse response = toScheduleManageResponse(schedule);

        return response;
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
