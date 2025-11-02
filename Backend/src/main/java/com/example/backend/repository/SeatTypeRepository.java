package com.example.backend.repository;

import com.example.backend.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, UUID> {
    List<SeatType> findAllByIsDeletedFalse();
    SeatType findByName(String name);
}
