package com.example.backend.service;


import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.RoomRequest;
import com.example.backend.dto.RoomResponse;
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
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findByIsDeletedFalseOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        Room room = new Room();
        room.setName(request.name());
        // default if null
        if (request.status() != null) {
            room.setStatus(Room.RoomStatus.valueOf(request.status()));
        } else {
            room.setStatus(Room.RoomStatus.active);
        }
        room.setDeleted(false);
        Room saved = roomRepository.save(room);
        return toResponse(saved);
    }

    @Transactional
    public RoomResponse updateRoom(UUID id, RoomRequest request) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        if (request.name() != null) existing.setName(request.name());
        if (request.status() != null) existing.setStatus(Room.RoomStatus.valueOf(request.status()));
        Room saved = roomRepository.save(existing);
        return toResponse(saved);
    }

    @Transactional
    public ApiResponse deleteRoom(UUID id) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        existing.setDeleted(true); // soft delete
        roomRepository.save(existing);
        return new ApiResponse("success", "Xóa phòng thành công");
    }

    private RoomResponse toResponse(Room r) {
        return new RoomResponse(r.getId(), r.getName(), r.getStatus().name(), r.isDeleted());
    }
}
