package com.example.backend.repository;

import com.example.backend.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, UUID> {
    List<ShowTime> findByIsDeletedFalseOrderByStartTimeAsc();
}
