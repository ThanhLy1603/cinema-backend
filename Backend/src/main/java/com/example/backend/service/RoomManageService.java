package com.example.backend.service;


import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.RoomMangeRequest;
import com.example.backend.dto.response.RoomManageResponse;
import com.example.backend.entity.Room;
import com.example.backend.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomManageService {
    private final RoomRepository roomRepository;
    private final SeatManageService seatManageService;

    @Transactional(readOnly = true)
    public List<RoomManageResponse> getAllRooms() {
        return roomRepository.findByIsDeletedFalseOrderByNameAsc()
                .stream()
                .map(this::toRoomResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse createRoom(RoomMangeRequest request) {
        Room room = new Room();
        room.setName(request.name());
        // default if null
        if (request.status() != null) {
            room.setStatus(Room.RoomStatus.valueOf(request.status()));
        } else {
            room.setStatus(Room.RoomStatus.active);
        }
        room.setDeleted(false);

        Room roomSaved = roomRepository.save(room);
        seatManageService.createSeat(roomSaved);
        return new ApiResponse("sucess", "Thêm phòng chiếu thành công");
    }

    @Transactional
    public ApiResponse updateRoom(UUID id, RoomMangeRequest request) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        if (request.name() != null) existing.setName(request.name());
        if (request.status() != null) existing.setStatus(Room.RoomStatus.valueOf(request.status()));
        roomRepository.save(existing);
        return new ApiResponse("sucess", "Sửa thông tin phòng chiếu thành công");
    }

    @Transactional
    public ApiResponse deleteRoom(UUID id) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        existing.setDeleted(true); // soft delete
        roomRepository.save(existing);
        return new ApiResponse("success", "Xóa phòng thành công");
    }

    private RoomManageResponse toRoomResponse(Room room) {
        return new RoomManageResponse(room.getId(), room.getName(), room.getStatus().name());
    }
}
