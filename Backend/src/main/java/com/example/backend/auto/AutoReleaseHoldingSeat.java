package com.example.backend.auto;

import com.example.backend.dto.response.SeatReservationResponse;
import com.example.backend.entity.ScheduleSeat;
import com.example.backend.repository.ScheduleSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AutoReleaseHoldingSeat {
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void releaseExpiredHoldingSeat() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduleSeat> expiredSeats = scheduleSeatRepository.findAllExpired(now);

        if (expiredSeats.isEmpty()) return;

        for (ScheduleSeat seat : expiredSeats) {
            seat.setStatus("available");
            seat.setHolderId(null);
            seat.setHoldExpiresAt(null);

            scheduleSeatRepository.save(seat);

            // Gửi realtime cho FE
            SeatReservationResponse response = new SeatReservationResponse();
            response.setSeatId(seat.getSeat().getId());
            response.setPosition(seat.getSeat().getPosition());
            response.setSeatType(seat.getSeat().getSeatType().getName());
            response.setStatus("available");
            response.setHolderId(null);
            response.setHoldExpiresAt(null);

            simpMessagingTemplate.convertAndSend(
                    "/topic/seats/" + seat.getSchedule().getId(),
                    response
            );
        }

        System.out.println("Đã reset toàn bộ ghế thành công");
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
}
