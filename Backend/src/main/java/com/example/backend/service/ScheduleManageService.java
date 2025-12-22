package com.example.backend.service;

import com.example.backend.dto.request.ScheduleManageRequest;
import com.example.backend.dto.response.*;
import com.example.backend.entity.Film;
import com.example.backend.entity.Room;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ShowTime;
import com.example.backend.repository.FilmRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.ScheduleRepository;
import com.example.backend.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleManageService {
    private final ScheduleRepository scheduleRepository;
    private final FilmRepository filmRepository;
    private final RoomRepository roomRepository;
    private final ShowTimeRepository showTimeRepository;
    private final int CLEAN_TIME = 15;

    @Transactional
    public List<ScheduleManageResponse> getAllSchedules() {
        List<ScheduleManageResponse> schedules = scheduleRepository.findByIsDeletedFalseOrderByScheduleDateDescShowTimeStartTimeAsc()
                .stream()
                .map(this::toScheduleManageResponse)
                .collect(Collectors.toList());

        return schedules;
    }

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
    public ApiResponse bulkSchedules(List<ScheduleManageRequest> requests) {
        System.out.println("requests: " + requests);
        List<Schedule> schedules = new ArrayList<>();

        for (ScheduleManageRequest request : requests) {
            Film film = filmRepository.findById(request.filmId()).orElse(null);
            Room room = roomRepository.findById(request.roomId()).orElse(null);
            ShowTime showTime = showTimeRepository.findById(request.showTimeId()).orElse(null);

            if (film == null) return new ApiResponse("error", "Phim không tồn tại");
            if (room == null) return new ApiResponse("error", "Phòng không tồn tại");
            if (showTime == null) return new ApiResponse("error", "Khung giờ không tồn tại");

            LocalDate scheduleDate = request.scheduleDate();

            Schedule schedule = Schedule.builder()
                    .film(film)
                    .room(room)
                    .showTime(showTime)
                    .scheduleDate(scheduleDate)
                    .isDeleted(false)
                    .build();

            System.out.println("schedule: " + schedule);
            schedules.add(schedule);
        }
        scheduleRepository.saveAll(schedules);
        return new ApiResponse("success", "Thêm lịch chiếu thành công");
    }

    @Transactional
    public ApiResponse createSchedule(ScheduleManageRequest request) {
        // 1. Lấy các thực thể
        Film film = filmRepository.findById(request.filmId()).orElse(null);
        Room room = roomRepository.findById(request.roomId()).orElse(null);
        ShowTime showTime = showTimeRepository.findById(request.showTimeId()).orElse(null);

        if (film == null) return new ApiResponse("error", "Phim không tồn tại");
        if (room == null) return new ApiResponse("error", "Phòng không tồn tại");
        if (showTime == null) return new ApiResponse("error", "Khung giờ không tồn tại");

        LocalDate scheduleDate = request.scheduleDate();

        // 2. Lấy tất cả lịch cùng phòng cùng ngày
        List<Schedule> schedulesInRoom = scheduleRepository.findByRoomAndScheduleDateAndIsDeletedFalse(room, scheduleDate);

        // Tính giờ bắt đầu và kết thúc thực tế của lịch mới (bao gồm 15 phút dọn dẹp)
        int newStart = showTime.getStartTime().getHour() * 60 + showTime.getStartTime().getMinute();
        int newEnd = newStart + film.getDuration() + CLEAN_TIME; // 15 phút dọn dẹp

        // 3. Tìm tất cả lịch bị xung đột
        List<String> conflictSchedules = schedulesInRoom.stream()
                .filter(s -> {
                    int existingStart = s.getShowTime().getStartTime().getHour() * 60 + s.getShowTime().getStartTime().getMinute();
                    int existingEnd = existingStart + s.getFilm().getDuration() + CLEAN_TIME; // thêm 15 phút dọn dẹp
                    return newStart < existingEnd && newEnd > existingStart;
                })
                .map(s -> {
                    int existingStart = s.getShowTime().getStartTime().getHour() * 60 + s.getShowTime().getStartTime().getMinute();
                    int existingEnd = existingStart + s.getFilm().getDuration() + CLEAN_TIME;
                    return "Phim \"" + s.getFilm().getName() +
                            "\", Phòng \"" + s.getRoom().getName() +
                            "\", Ngày " + s.getScheduleDate() +
                            ", Giờ chiếu thực tế: " + toTimeString(existingStart) +
                            " - " + toTimeString(existingEnd) +
                            " (thời gian vệ sinh đã cộng)" +
                            ", Thời lượng phim: " + s.getFilm().getDuration() + " phút";
                })
                .toList();

        if (!conflictSchedules.isEmpty()) {
            // Tạo message đầy đủ gồm lịch mới và tất cả lịch trùng
            StringBuilder message = new StringBuilder();
            message.append("Xung đột lịch chiếu!\n");
            message.append("Lịch mới: Phim \"" + film.getName() + "\", Phòng \"" + room.getName() +
                    "\", Ngày " + scheduleDate +
                    ", Giờ chiếu thực tế: " + toTimeString(newStart) + " - " + toTimeString(newEnd) +
                    " (thời gian vệ sinh đã cộng)" +
                    ", Thời lượng phim: " + film.getDuration() + " phút\n");
            message.append("Các lịch trùng:\n");
            conflictSchedules.forEach(s -> message.append("- ").append(s).append("\n"));

            return new ApiResponse("error", message.toString());
        }

        Schedule schedule = Schedule.builder()
                .film(film)
                .room(room)
                .showTime(showTime)
                .scheduleDate(scheduleDate)
                .isDeleted(false)
                .build();

        System.out.println("schedule: " + schedule);

        scheduleRepository.save(schedule);

        return new ApiResponse("success", "Thêm lịch chiếu thành công");
    }

    @Transactional
    public ApiResponse updateSchedule(UUID id, ScheduleManageRequest request) {
        System.out.println("id: " + id);
        System.out.println("request: " + request);
        Film film = filmRepository.findById(request.filmId()).orElse(null);
        Room room = roomRepository.findById(request.roomId()).orElse(null);
        ShowTime showTime = showTimeRepository.findById(request.showTimeId()).orElse(null);

        if (film == null) return new ApiResponse("error", "Phim không tồn tại");
        if (room == null) return new ApiResponse("error", "Phòng không tồn tại");
        if (showTime == null) return new ApiResponse("error", "Khung giờ không tồn tại");

        LocalDate scheduleDate = request.scheduleDate();

        // Lấy tất cả lịch cùng phòng cùng ngày
        List<Schedule> schedulesInRoom = scheduleRepository.findByRoomAndScheduleDateAndIsDeletedFalse(room, scheduleDate)
                .stream().filter(schedule -> !schedule.getId().equals(id)).toList();

        // Tính giờ bắt đầu và kết thúc thực tế của lịch mới (bao gồm 15 phút dọn dẹp)
        int newStart = showTime.getStartTime().getHour() * 60 + showTime.getStartTime().getMinute();
        int newEnd = newStart + film.getDuration() + CLEAN_TIME; // 15 phút dọn dẹp

        // Tìm tất cả lịch bị xung đột
        List<String> conflictSchedules = schedulesInRoom.stream()
                .filter(s -> {
                    int existingStart = s.getShowTime().getStartTime().getHour() * 60 + s.getShowTime().getStartTime().getMinute();
                    int existingEnd = existingStart + s.getFilm().getDuration() + CLEAN_TIME; // thêm 15 phút dọn dẹp
                    return newStart < existingEnd && newEnd > existingStart;
                })
                .map(s -> {
                    int existingStart = s.getShowTime().getStartTime().getHour() * 60 + s.getShowTime().getStartTime().getMinute();
                    int existingEnd = existingStart + s.getFilm().getDuration() + CLEAN_TIME;
                    return "Phim \"" + s.getFilm().getName() +
                            "\", Phòng \"" + s.getRoom().getName() +
                            "\", Ngày " + s.getScheduleDate() +
                            ", Giờ chiếu thực tế: " + toTimeString(existingStart) +
                            " - " + toTimeString(existingEnd) +
                            " (thời gian vệ sinh đã cộng)" +
                            ", Thời lượng phim: " + s.getFilm().getDuration() + " phút";
                })
                .toList();

        if (!conflictSchedules.isEmpty()) {
            // Tạo message đầy đủ gồm lịch mới và tất cả lịch trùng
            StringBuilder message = new StringBuilder();
            message.append("Xung đột lịch chiếu!\n");
            message.append("Lịch mới: Phim \"" + film.getName() + "\", Phòng \"" + room.getName() +
                    "\", Ngày " + scheduleDate +
                    ", Giờ chiếu thực tế: " + toTimeString(newStart) + " - " + toTimeString(newEnd) +
                    " (thời gian vệ sinh đã cộng)" +
                    ", Thời lượng phim: " + film.getDuration() + " phút\n");
            message.append("Các lịch trùng:\n");
            conflictSchedules.forEach(s -> message.append("- ").append(s).append("\n"));

            return new ApiResponse("error", message.toString());
        }

        Schedule existSchedule = scheduleRepository.findById(id).orElse(null);
        assert existSchedule != null;
        existSchedule.setIsDeleted(true);
        System.out.println("existSchedule: " + existSchedule);

        Schedule savedSchedule = new Schedule();
        savedSchedule.setFilm(film);
        savedSchedule.setRoom(room);
        savedSchedule.setShowTime(showTime);
        savedSchedule.setScheduleDate(scheduleDate);
        savedSchedule.setIsDeleted(false);
        scheduleRepository.save(savedSchedule);

        return new ApiResponse("success", "Sửa lịch chiếu thành công");
    }

    public ApiResponse deleteSchedule(UUID id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        assert schedule != null;
        schedule.setIsDeleted(true);
        scheduleRepository.save(schedule);

        return new ApiResponse("success", "Xoá lịch chiếu thành công");
    }

    // Helper: chuyển phút sang HH:mm
    private String toTimeString(int totalMinutes) {
        int h = totalMinutes / 60;
        int m = totalMinutes % 60;
        return String.format("%02d:%02d", h, m);
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
