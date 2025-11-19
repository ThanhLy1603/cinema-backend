package com.example.backend.service;

import com.example.backend.dto.SeatTypeManageResponse;
import com.example.backend.entity.SeatType;
import com.example.backend.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatTypeService {
    private final SeatTypeRepository seatTypeRepository;

    public List<SeatTypeManageResponse> getAllSeatTypes() {
        List<SeatTypeManageResponse> seatTypes = seatTypeRepository.findAllByIsDeletedFalse().stream()
                .map(this::toSeatTypeManageResponse)
                .collect(Collectors.toUnmodifiableList());

        return seatTypes;
    }

    private SeatTypeManageResponse toSeatTypeManageResponse(SeatType seatType) {
        return new  SeatTypeManageResponse(
            seatType.getId(),
            seatType.getName()
        );
    }
}
