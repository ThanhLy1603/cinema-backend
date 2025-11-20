package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CheckoutRequest;
import com.example.backend.dto.HoldRequest;
import com.example.backend.dto.SeatReservationResponse;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.ScheduleSeat;
import com.example.backend.entity.Seat;
import com.example.backend.repository.ScheduleRepository;
import com.example.backend.repository.ScheduleSeatRepository;
import com.example.backend.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatReservationService {
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;

    private static final int DEFAULT_HOLDER_MINUTES = 10;

    @Transactional
    public List<SeatReservationResponse> getSeats(UUID scheduleId) {
        List<ScheduleSeat> list = scheduleSeatRepository.findByScheduleIdOrderBySeatPosition(scheduleId);

        return list.stream()
                .map(this::toSeatReservationResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse holdSeat(UUID scheduleId, UUID seatId, HoldRequest request) {
        String holderId = request.getHolderId();
        int ttlMinutes = request.getHoldMinutes() != null
                ? request.getHoldMinutes() : DEFAULT_HOLDER_MINUTES;

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));

        ScheduleSeat scheduleSeat = scheduleSeatRepository
                .findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                .orElseGet(() -> {
                    ScheduleSeat newSeat = new ScheduleSeat();
                    newSeat.setSchedule(schedule);
                    newSeat.setSeat(seat);
                    newSeat.setStatus("available");
                    return scheduleSeatRepository.save(newSeat);
                });

        if ("holding".equals(scheduleSeat.getStatus())
                && scheduleSeat.getHoldExpiresAt().isAfter(LocalDateTime.now())
                && !Objects.equals(scheduleSeat.getHolderId(), holderId)) {
            return new ApiResponse("fail","Ghế đang được giữ bởi người khác");
        }

        // Nếu booked → cấm giữ
        if ("booked".equals(scheduleSeat.getStatus())) {
            return new ApiResponse("fail","Ghế đã được đặt");
        }

        scheduleSeat.setStatus("holding");
        scheduleSeat.setHolderId(holderId);
        scheduleSeat.setHoldExpiresAt(LocalDateTime.now().plusMinutes(ttlMinutes));
        scheduleSeatRepository.save(scheduleSeat);

        return new ApiResponse("success", "Giữ ghế thành công");
    }

    @Transactional
    public void releaseSeat(UUID scheduleId, UUID seatId, String holderId) {
        ScheduleSeat scheduleSeat = scheduleSeatRepository
                .findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!"holding".equals(scheduleSeat.getStatus())) {
            return;
        }

        if (!Objects.equals(scheduleSeat.getHolderId(), holderId)) {
            throw new RuntimeException("You are not holder of this seat");
        }

        scheduleSeat.setStatus("available");
        scheduleSeat.setHolderId(null);
        scheduleSeat.setHoldExpiresAt(null);
        scheduleSeatRepository.save(scheduleSeat);
    }

    @Transactional
    public List<SeatReservationResponse> checkout(UUID scheduleId, CheckoutRequest request) {
        String holderId = request.getHolderId();
        List<UUID> seatIds = request.getSeatIds();
        List<SeatReservationResponse> results = new ArrayList<>();

        for (UUID seatId : seatIds) {
            ScheduleSeat scheduleSeat = scheduleSeatRepository
                    .findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));

            if (!"holding".equals(scheduleSeat.getStatus()))
                throw new RuntimeException("Seat not held");

            if (!Objects.equals(scheduleSeat.getHolderId(), holderId))
                throw new RuntimeException("Not your hold");

            if (scheduleSeat.getHoldExpiresAt().isBefore(LocalDateTime.now()))
                throw new RuntimeException("Hold expired");

            scheduleSeat.setStatus("booked");
            scheduleSeat.setHoldExpiresAt(null);

            scheduleSeatRepository.save(scheduleSeat);

            results.add(toSeatReservationResponse(scheduleSeat));
        }

        return results;
    }


    private SeatReservationResponse toSeatReservationResponse(ScheduleSeat ss) {
        SeatReservationResponse dto = new SeatReservationResponse();
        dto.setSeatId(ss.getSeat().getId());
        dto.setPosition(ss.getSeat().getPosition());
        dto.setStatus(ss.getStatus());
        dto.setHolderId(ss.getHolderId());
        dto.setHoldExpiresAt(ss.getHoldExpiresAt());
        return dto;
    }
}
