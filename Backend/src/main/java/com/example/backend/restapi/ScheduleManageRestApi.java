package com.example.backend.restapi;

import com.example.backend.dto.ScheduleManageResponse;
import com.example.backend.service.ScheduleManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/schedules")
@RequiredArgsConstructor
public class ScheduleManageRestApi {
    private final ScheduleManageService scheduleManageService;

    @GetMapping("")
    public ResponseEntity<List<ScheduleManageResponse>> getAllSchedules() {
        return ResponseEntity.ok(scheduleManageService.getAllSchedules());
    }
}
