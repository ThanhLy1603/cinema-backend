package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.Room;
import com.example.backend.entity.Seat;
import com.example.backend.entity.SeatType;
import com.example.backend.repository.SeatRepository;
import com.example.backend.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatManageService {
    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;

    @Transactional
    public List<SeatManageResponse> getAllSeats() {
        List<SeatManageResponse> seats = seatRepository.findAllByIsDeletedFalse().stream()
                .map(this::toSeatManageResponse)
                .collect(Collectors.toList());

        return seats;
    }

    @Transactional
    public List<SeatManageResponse> getSeatsByRoomId(UUID roomId) {
        List<SeatManageResponse> seats = seatRepository.findAllByIsDeletedFalseAndRoomId(roomId).stream()
                .map(this::toSeatManageResponse)
                .collect(Collectors.toList());
        return seats;
    }

    @Transactional
    public List<SeatManageResponse> getSeatsBySeatTypeId(UUID seatTypeId) {
        List<SeatManageResponse>  seats = seatRepository.findAllByIsDeletedFalseAndSeatTypeId(seatTypeId).stream()
                .map(this::toSeatManageResponse)
                .collect(Collectors.toList());
        return seats;
    }

    @Transactional
    public List<SeatManageResponse> getSeatsByRoomIdAndTypeId(UUID roomId, UUID seatTypeId) {
        List<SeatManageResponse> seats = seatRepository.findAllByIsDeletedFalseAndRoomIdAndSeatTypeId(roomId, seatTypeId)
                .stream().map(this::toSeatManageResponse)
                .collect(Collectors.toList());

        return seats;
    }

    @Transactional
    public void createSeat(Room room) {
        SeatType thuong = seatTypeRepository.findByName("Ghế Thường");
        SeatType vip = seatTypeRepository.findByName("Ghế VIP");
        SeatType couple = seatTypeRepository.findByName("Ghế Couple");

        // Total seats: 1 -> 156
        int totalSeats = 156;

        for (int number = 1; number <= totalSeats; number++) {

            // Determine row letter
            String rowLetter =
                    (number <= 15)  ? "A" :
                    (number <= 30)  ? "B" :
                    (number <= 45)  ? "C" :
                    (number <= 60)  ? "D" :
                    (number <= 75)  ? "E" :
                    (number <= 90)  ? "F" :
                    (number <= 105) ? "G" :
                    (number <= 120) ? "H" :
                    (number <= 135) ? "I" :
                    (number <= 150) ? "J" :
                    "K";

            // Determine SeatType
            SeatType seatType;

            // Ghế VIP
            if ((number >= 49 && number <= 57) ||
                    (number >= 64 && number <= 72) ||
                    (number >= 79 && number <= 87) ||
                    (number >= 94 && number <= 102)) {
                seatType = vip;
            }
            // Ghế Couple
            else if (number >= 151 && number <= 156) {
                seatType = couple;
            }
            // Ghế Thường
            else {
                seatType = thuong;
            }

            // Position like A1, B25, K156
            String position = rowLetter + number;

            Seat seat = new Seat();
            seat.setRoom(room);
            seat.setSeatType(seatType);
            seat.setPosition(position);
            seat.setActive(true);
            seat.setDeleted(false);

            seatRepository.save(seat);
        }
    }

    public ApiResponse updateSeat(UUID id, SeatManageRequest request) {
        Seat existSeat = seatRepository.findById(id).orElse(null);
        SeatType seatType = seatTypeRepository.findById(request.seatTypeId()).orElse(null);

        if (existSeat == null) {
            return new ApiResponse("error", "Không tìm thấy ghế cần sửa");
        }

        if (seatType == null) {
            return new ApiResponse("error", "Không tìm loại ghế");
        }

        existSeat.setDeleted(true);
        seatRepository.save(existSeat);

        Seat newSeat = new Seat();
        newSeat.setPosition(existSeat.getPosition());
        newSeat.setRoom(existSeat.getRoom());
        newSeat.setSeatType(seatType);
        newSeat.setActive(request.active());
        newSeat.setDeleted(false);
        seatRepository.save(newSeat);

        return new ApiResponse("success", "Chỉnh sửa thông tin ghế thành công");
    }

    private SeatManageResponse toSeatManageResponse(Seat seat) {
        return new SeatManageResponse(
            seat.getId(),
            seat.getPosition(),
            seat.isActive(),
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
