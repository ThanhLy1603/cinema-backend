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
import com.example.backend.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatReservationService {
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final int DEFAULT_HOLDER_MINUTES = 10;
    private final ListableBeanFactory listableBeanFactory;

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

        List<ScheduleSeat> allSeats = scheduleSeatRepository.findByScheduleIdOrderBySeatPosition(scheduleId);
        if (createsGapWhenHolding(allSeats, scheduleSeat,  holderId)) {
            System.out.println("Không được để 1 ghế trống giữa hai ghế đã giữ/đặt");
            return new ApiResponse("fail", "Không được để 1 ghế trống giữa hai ghế đã giữ/đặt");
        }

        scheduleSeat.setStatus("holding");
        scheduleSeat.setHolderId(holderId);
        scheduleSeat.setHoldExpiresAt(LocalDateTime.now().plusMinutes(ttlMinutes));
        scheduleSeatRepository.save(scheduleSeat);

        simpMessagingTemplate.convertAndSend("/topic/seats/" +scheduleId, toSeatReservationResponse(scheduleSeat));

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
        simpMessagingTemplate.convertAndSend("/topic/seats/" + scheduleId, toSeatReservationResponse(scheduleSeat));
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

    private SeatReservationResponse toSeatReservationResponse(ScheduleSeat scheduleSeat) {
        SeatReservationResponse response = new SeatReservationResponse();
        response.setSeatId(scheduleSeat.getSeat().getId());
        response.setPosition(scheduleSeat.getSeat().getPosition());
        response.setSeatType(scheduleSeat.getSeat().getSeatType().getName());
        response.setStatus(scheduleSeat.getStatus());
        response.setHolderId(scheduleSeat.getHolderId());
        response.setHoldExpiresAt(scheduleSeat.getHoldExpiresAt());
        return response;
    }

    private boolean createsGapWhenHolding(List<ScheduleSeat> scheduleSeats, ScheduleSeat targetSeat, String currentHolderId) {
        Map<String, List<RowSeat>> rows = buildRows(scheduleSeats);

        String targetPosition = targetSeat.getSeat().getPosition();
        String rowKey = targetPosition.substring(0, 1);

        List<RowSeat> rowSeats = rows.get(rowKey);
        if (rowSeats == null) return false;

        for (RowSeat rowSeat : rowSeats) {
            if (rowSeat.position.equals(targetPosition)) {
                rowSeat.simulatedStatus = "holding";
                rowSeat.simulatedHolderId = currentHolderId;
            }
        }

        for (int i = 0; i < rowSeats.size(); i++) {
            RowSeat left = rowSeats.get(i);
            RowSeat middle = rowSeats.get(i + 1);
            RowSeat right = rowSeats.get(i + 2);

            boolean leftTaken = isTaken(left);
            boolean rightTaken = isTaken(right);
            boolean middleTaken = isTaken(middle);

            if (leftTaken && rightTaken && middleTaken) return true;
        }

        return false;
    }

    private boolean isTaken(RowSeat seat) {
        if ("booked".equalsIgnoreCase(seat.status)) return true;
        if ("holding".equalsIgnoreCase(seat.status) && seat.holdExpiresAt != null && seat.holdExpiresAt.isAfter(LocalDateTime.now())) return true;
        if ("holding".equalsIgnoreCase(seat.simulatedStatus) && seat.simulatedHolderId != null) return true;
        if ("booked".equalsIgnoreCase(seat.simulatedStatus)) return true;
        return false;
    }

    private boolean isFree(RowSeat seat) {
        if (seat.status == null) return true;
        if ("available".equalsIgnoreCase(seat.status)) {
            return !"holding".equalsIgnoreCase(seat.simulatedStatus) && !"booked".equalsIgnoreCase(seat.simulatedStatus);
        }
        if ("holding".equalsIgnoreCase(seat.status) && (seat.holdExpiresAt == null || seat.holdExpiresAt.isBefore(LocalDateTime.now()))) return true;
        return false;
    }

    private Map<String, List<RowSeat>> buildRows(List<ScheduleSeat> scheduleSeats) {
        Map<String, List<RowSeat>> rows = new HashMap<>();
        for (ScheduleSeat scheduleSeat : scheduleSeats) {
            String position = scheduleSeat.getSeat().getPosition();
            String rowKey = position.substring(0, 1);
            int index;

            try {
                index = Integer.parseInt(position.substring(1));
            } catch (Exception ex) {
                index = 0;
            }

            RowSeat rowSeat = new RowSeat();
            rowSeat.position = position;
            rowSeat.status = scheduleSeat.getStatus();
            rowSeat.holderId = scheduleSeat.getHolderId();
            rowSeat.positionIndex = index;
            rowSeat.holdExpiresAt = scheduleSeat.getHoldExpiresAt();
        }

        rows.values().forEach(list -> list.sort(Comparator.comparingInt(row -> row.positionIndex)));

        return rows;
    }

    private static class RowSeat {
        String position;
        String status;
        String holderId;
        LocalDateTime holdExpiresAt;
        int positionIndex;
        String simulatedStatus;
        String simulatedHolderId;
    }
}
