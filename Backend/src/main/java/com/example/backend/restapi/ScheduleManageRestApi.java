package com.example.backend.restapi;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ScheduleManageRequest;
import com.example.backend.dto.ScheduleManageResponse;
import com.example.backend.service.ScheduleManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/schedules")
@RequiredArgsConstructor
public class ScheduleManageRestApi {
    private final ScheduleManageService scheduleManageService;

    @GetMapping("")
    public ResponseEntity<List<ScheduleManageResponse>> getAllSchedules() {
        return ResponseEntity.ok(scheduleManageService.getAllSchedules());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSchedule(@RequestBody ScheduleManageRequest request) {
        return ResponseEntity.ok(scheduleManageService.createSchedule(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse> bulkSchedules(@RequestBody List<ScheduleManageRequest> requests) {
        return ResponseEntity.ok(scheduleManageService.bulkSchedules(requests));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSchedule(@PathVariable UUID id, @RequestBody ScheduleManageRequest request) {
        return ResponseEntity.ok(scheduleManageService.updateSchedule(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSchedule(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleManageService.deleteSchedule(id));
    }
}

