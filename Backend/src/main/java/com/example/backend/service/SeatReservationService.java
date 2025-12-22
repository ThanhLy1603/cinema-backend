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

    @Transactional
    public List<SeatReservationResponse> getSeats(UUID scheduleId) {
        List<ScheduleSeat> list =
                scheduleSeatRepository.findByScheduleIdOrderBySeatPosition(scheduleId);

        return list.stream()
                .map(this::toSeatReservationResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // HOLD SEAT
    // =========================
    @Transactional
    public ApiResponse holdSeat(UUID scheduleId, UUID seatId, HoldRequest request) {

        String holderId = request.getHolderId();
        int ttlMinutes =
                request.getHoldMinutes() != null ? request.getHoldMinutes() : DEFAULT_HOLDER_MINUTES;

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        ScheduleSeat scheduleSeat =
                scheduleSeatRepository.findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                        .orElseGet(() -> {
                            ScheduleSeat ss = new ScheduleSeat();
                            ss.setSchedule(schedule);
                            ss.setSeat(seat);
                            ss.setStatus("available");
                            return scheduleSeatRepository.save(ss);
                        });

        // Nếu đang được giữ bởi người khác
        if ("holding".equals(scheduleSeat.getStatus()) &&
                scheduleSeat.getHoldExpiresAt() != null &&
                scheduleSeat.getHoldExpiresAt().isAfter(LocalDateTime.now()) &&
                !Objects.equals(scheduleSeat.getHolderId(), holderId)) {

            return new ApiResponse("fail", "Ghế đang được giữ bởi người khác");
        }

        // Nếu booked thì không được giữ
        if ("booked".equals(scheduleSeat.getStatus())) {
            return new ApiResponse("fail", "Ghế đã được đặt");
        }

        List<ScheduleSeat> allSeats =
                scheduleSeatRepository.findByScheduleIdOrderBySeatPosition(scheduleId);

        if (createsGapWhenHolding(allSeats, scheduleSeat, holderId)) {
            return new ApiResponse("fail", "Không được để 1 ghế trống giữa hai ghế đã giữ/đặt");
        }

        scheduleSeat.setStatus("holding");
        scheduleSeat.setHolderId(holderId);
        scheduleSeat.setHoldExpiresAt(LocalDateTime.now().plusMinutes(ttlMinutes));

        scheduleSeatRepository.save(scheduleSeat);

        simpMessagingTemplate.convertAndSend(
                "/topic/seats/" + scheduleId,
                toSeatReservationResponse(scheduleSeat)
        );

        return new ApiResponse("success", "Giữ ghế thành công");
    }

    // =========================
    // RELEASE SEAT
    // =========================
    @Transactional
    public void releaseSeat(UUID scheduleId, UUID seatId, String holderId) {

        ScheduleSeat scheduleSeat =
                scheduleSeatRepository.findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                        .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (!"holding".equals(scheduleSeat.getStatus())) return;

        if (!Objects.equals(scheduleSeat.getHolderId(), holderId)) {
            throw new RuntimeException("You are not holder of this seat");
        }

        scheduleSeat.setStatus("available");
        scheduleSeat.setHolderId(null);
        scheduleSeat.setHoldExpiresAt(null);

        scheduleSeatRepository.save(scheduleSeat); // SAVE TRƯỚC

        simpMessagingTemplate.convertAndSend(
                "/topic/seats/" + scheduleId,
                toSeatReservationResponse(scheduleSeat)
        ); // GỬI SAU
    }

    // =========================
    // CHECKOUT
    // =========================
    @Transactional
    public List<SeatReservationResponse> checkout(UUID scheduleId, CheckoutRequest request) {
        String holderId = request.getHolderId();
        List<UUID> seatIds = request.getSeatIds();
        List<SeatReservationResponse> results = new ArrayList<>();

        for (UUID seatId : seatIds) {
            ScheduleSeat scheduleSeat =
                    scheduleSeatRepository.findByScheduleIdAndSeatIdForUpdate(scheduleId, seatId)
                            .orElseThrow(() -> new RuntimeException("Seat not found"));

            if (!"holding".equals(scheduleSeat.getStatus()))
                throw new RuntimeException("Seat not held");

            if (!Objects.equals(scheduleSeat.getHolderId(), holderId))
                throw new RuntimeException("Not your hold");

            if (scheduleSeat.getHoldExpiresAt().isBefore(LocalDateTime.now()))
                throw new RuntimeException("Hold expired");

            scheduleSeat.setStatus("booked");
            scheduleSeat.setHoldExpiresAt(null);

            scheduleSeatRepository.save(scheduleSeat);

            SeatReservationResponse dto = toSeatReservationResponse(scheduleSeat);
            results.add(dto);

            // BẮN WEBSOCKET CHO FE REALTIME
            simpMessagingTemplate.convertAndSend(
                    "/topic/seats/" + scheduleId,
                    dto
            );
        }

        return results;
    }

    // =========================
    // MAPPING
    // =========================
    private SeatReservationResponse toSeatReservationResponse(ScheduleSeat scheduleSeat) {
        SeatReservationResponse dto = new SeatReservationResponse();
        dto.setSeatId(scheduleSeat.getSeat().getId());
        dto.setPosition(scheduleSeat.getSeat().getPosition());
        dto.setSeatType(scheduleSeat.getSeat().getSeatType().getName());
        dto.setStatus(scheduleSeat.getStatus());
        dto.setHolderId(scheduleSeat.getHolderId());
        dto.setHoldExpiresAt(scheduleSeat.getHoldExpiresAt());
        return dto;
    }

    // =========================
    // GAP VALIDATION
    // =========================
    private boolean createsGapWhenHolding(List<ScheduleSeat> scheduleSeats,
                                          ScheduleSeat targetSeat,
                                          String currentHolderId) {

        Map<String, List<RowSeat>> rows = buildRows(scheduleSeats);

        String pos = targetSeat.getSeat().getPosition();
        String rowKey = pos.substring(0, 1);

        List<RowSeat> row = rows.get(rowKey);
        if (row == null) return false;

        for (RowSeat r : row) {
            if (r.position.equals(pos)) {
                r.simulatedStatus = "holding";
                r.simulatedHolderId = currentHolderId;
            }
        }

        for (int i = 0; i + 2 < row.size(); i++) {
            RowSeat left = row.get(i);
            RowSeat mid = row.get(i + 1);
            RowSeat right = row.get(i + 2);

            boolean leftTaken = isTaken(left);
            boolean rightTaken = isTaken(right);
            boolean midFree = !isTaken(mid);

            if (leftTaken && rightTaken && midFree) return true;
        }

        return false;
    }

    private boolean isTaken(RowSeat seat) {
        if ("booked".equalsIgnoreCase(seat.status)) return true;
        if ("holding".equalsIgnoreCase(seat.status) &&
                seat.holdExpiresAt != null &&
                seat.holdExpiresAt.isAfter(LocalDateTime.now())) return true;
        if ("holding".equalsIgnoreCase(seat.simulatedStatus)) return true;
        return false;
    }

    private Map<String, List<RowSeat>> buildRows(List<ScheduleSeat> scheduleSeats) {

        Map<String, List<RowSeat>> rows = new HashMap<>();

        for (ScheduleSeat ss : scheduleSeats) {
            String pos = ss.getSeat().getPosition();
            String rowKey = pos.substring(0, 1);

            int index;
            try {
                index = Integer.parseInt(pos.substring(1));
            } catch (Exception ex) {
                index = 0;
            }

            RowSeat r = new RowSeat();
            r.position = pos;
            r.status = ss.getStatus();
            r.holderId = ss.getHolderId();
            r.positionIndex = index;
            r.holdExpiresAt = ss.getHoldExpiresAt();

            rows.computeIfAbsent(rowKey, k -> new ArrayList<>()).add(r);
        }

        rows.values()
                .forEach(list -> list.sort(Comparator.comparingInt(x -> x.positionIndex)));

        return rows;
    }

    // DTO nội bộ để validate
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
