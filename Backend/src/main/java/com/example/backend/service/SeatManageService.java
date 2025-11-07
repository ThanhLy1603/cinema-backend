package com.example.backend.service;

import com.example.backend.dto.RoomManageResponse;
import com.example.backend.dto.SeatManageResponse;
import com.example.backend.dto.SeatTypeManageResponse;
import com.example.backend.entity.Room;
import com.example.backend.entity.Seat;
import com.example.backend.entity.SeatType;
import com.example.backend.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatManageService {
    private final SeatRepository seatRepository;

    @Transactional
    public List<SeatManageResponse> getAllSeats() {
        List<SeatManageResponse> seats = seatRepository.findAllByIsDeletedFalse().stream()
                .map(this::toSeatManageResponse)
                .collect(Collectors.toList());

        return seats;
    }

    private SeatManageResponse toSeatManageResponse(Seat seat) {
        return new SeatManageResponse(
            seat.getId(),
            seat.getPosition(),
            toRoomManageResponse(seat.getRoom()),
            toSeatTypeManageResponse(seat.getSeatType())
        );
    }

    private RoomManageResponse toRoomManageResponse(Room room) {
        return new RoomManageResponse(
                room.getId(),
                room.getName(),
                room.getStatus().name()
        );
    }

    private SeatTypeManageResponse toSeatTypeManageResponse(SeatType seatType) {
        return new SeatTypeManageResponse(
               seatType.getId(),
               seatType.getName()
        );
    }
}
