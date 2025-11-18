package com.example.backend.restapi;


import com.example.backend.dto.ScheduleManageResponse;
import com.example.backend.service.ScheduleCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/schedules")
@RequiredArgsConstructor
public class ScheduleCustomerApi {
    private final ScheduleCustomerService scheduleCustomerService;

    @GetMapping("/{filmId}")
    public ResponseEntity<List<ScheduleManageResponse>> getAllSchedulesByFilmId(@PathVariable UUID filmId) {
        return ResponseEntity.ok(scheduleCustomerService.getSchedulesByFilmId(filmId));
    }
}
